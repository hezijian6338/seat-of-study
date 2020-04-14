package com.study.room.service.impl;

import com.study.room.dao.FootprintMapper;
import com.study.room.dto.FootprintDTO;
import com.study.room.model.Footprint;
import com.study.room.service.FootprintService;
import com.study.room.core.AbstractService;
import com.study.room.utils.Tools;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.locks.Condition;


/**
 * Created by CodeGenerator on 2020/03/21.
 */
@Service
@Transactional
public class FootprintServiceImpl extends AbstractService<Footprint> implements FootprintService {
    @Resource
    private FootprintMapper footprintMapper;

    /**
     * @param footprintDTO
     * @Method haveSeat
     * TODO: 找到座位坐下
     * @Return boolean
     * @Exception
     * @Date 2020/4/5 8:03 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public boolean haveSeat(FootprintDTO footprintDTO) {
        // TODO: 检查该用户之前是否有借座, 但是忘记离开座位的记录 (手动触发, 结束状态, 并且自动计算选择的自习时长)
        int time = 0;
        if (footprintDTO.getUserId() != null)
            // 直接调用检查时间的方法 (有类似的逻辑)
            time = this.checkTime(footprintDTO.getUserId());
        else
            return false;

        // 还有剩余时间 (上一次借座还在继续)
        if (time > 0)
            return false;

        // TODO: 完成基础逻辑
        Footprint footprint = new Footprint();

        // 直接映射过去, 填充完整
        BeanUtils.copyProperties(footprintDTO, footprint);

        // 构建新记录, 所以需要 id
        if (footprint.getId() == null)
            footprint.setId(Tools.getUUID());

        // 初始化学习时间
        footprint.setStayTime(0);

        // 如果没有填写学习时间, 默认一个小时 (其实是必填的)
        if (footprint.getWantedTime() == 0)
            footprint.setWantedTime(60 * 60);

        // 设置状态为 坐下
        footprint.setStatus(Footprint.STATUS.IN);

        // 时间填充需要补充 (√)
        footprint.setCreatedTime(Tools.getTimeStamp());
//        footprint.setUpdatedTime(Tools.getTimeStamp());

        this.save(footprint);

        return true;
    }

    /**
     * @param userId
     * @Method leaveSeat
     * TODO: 离开正在使用的座位
     * @Return boolean
     * @Exception
     * @Date 2020/4/5 8:04 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public boolean leaveSeat(String userId) {
        // 需要添加一个方法, 根据状态和当前自习室编号座位号进行查询现有的座位足迹信息 (√)

        // TODO: 根据新增加的方法, 返回找到的座位足迹信息
        Footprint footprint = this.findUseSeatByUserId(userId);

        Timestamp current_time = Tools.getTimeStamp();

        // 判断状态是否从正常坐下状态
        if (footprint.getStatus() == Footprint.STATUS.IN) {
//            Footprint footprint = new Footprint();

            // 直接映射过去, 填充完整
//            BeanUtils.copyProperties(footprintDTO, footprint);

            // 更新当前时间为离开时间
            footprint.setUpdatedTime(Tools.getTimeStamp());

            // 状态修改为离开
            footprint.setStatus(Footprint.STATUS.OUT);

            // TODO: 填充一共总自习时间

            // 存在过暂离的情况, 自习时间本不为空
            int staty_time = footprint.getStayTime();
            // 如果暂离不为空, 即是曾经暂离过
            if (staty_time != 0) {
                // 计算当次自习时间
                staty_time = Math.toIntExact((current_time.getTime() - footprint.getUpdatedTime().getTime())
                        // 小于自定义时间
                        < footprint.getWantedTime()
                        // 当前时间 - 更新时间 + 已经自习时间
                        ? (current_time.getTime() - footprint.getUpdatedTime().getTime() + footprint.getStayTime())
                        // 最大自习时间 + 已经自习时间
                        : footprint.getWantedTime() + footprint.getStayTime());
                footprint.setStayTime(staty_time);
            } else {
                // 没有进行过暂离 (自习时间为空)
                staty_time = Math.toIntExact((current_time.getTime() - footprint.getUpdatedTime().getTime())
                        // 小于自定义时间
                        < footprint.getWantedTime()
                        // 当前时间 - 更新时间
                        ? (current_time.getTime() - footprint.getUpdatedTime().getTime())
                        // 最大自习时间
                        : footprint.getWantedTime());
                footprint.setStayTime(staty_time);
            }

            // 跟新足迹内容
            this.update(footprint);

            return true;
        }

        // 判断状态是否从暂离状态离开
        if (footprint.getStatus() == Footprint.STATUS.TEMP) {
            // 更新当前时间为离开时间
            footprint.setUpdatedTime(Tools.getTimeStamp());

            // 状态修改为离开
            footprint.setStatus(Footprint.STATUS.OUT);

            // TODO: 填充一共总自习时间

            // 存在过暂离的情况, 自习时间本不为空
            int staty_time = footprint.getStayTime();
            // 曾经多次暂离
            if (staty_time != 0) {
                // 计算当次自习时间
                staty_time = Math.toIntExact((current_time.getTime() - footprint.getUpdatedTime().getTime())
                        // 小于自定义时间
                        < footprint.getWantedTime()
                        // 当前时间 - 更新时间 + 已经自习时间
                        ? (current_time.getTime() - footprint.getUpdatedTime().getTime() + footprint.getStayTime())
                        // 最大自习时间 + 已经自习时间
                        : footprint.getWantedTime() + footprint.getStayTime());
                footprint.setStayTime(staty_time);
            } else {
                // 没有进行过暂离 (自习时间为空)
                staty_time = Math.toIntExact((current_time.getTime() - footprint.getUpdatedTime().getTime())
                        // 小于自定义时间
                        < footprint.getWantedTime()
                        // 当前时间 - 更新时间
                        ? (current_time.getTime() - footprint.getUpdatedTime().getTime())
                        // 最大自习时间
                        : footprint.getWantedTime());
                footprint.setStayTime(staty_time);
            }

            // 跟新足迹内容
            this.update(footprint);

            return true;
        }

        return false;
    }

    /**
     * @Method pauseSeat
     * TODO: 根据完整的足迹实体来进行暂时离开座位的操作 (但其实可以直接根据 user_id来进行操作)
     * @param footprintDTO
     * @Return boolean
     * @Exception
     * @Date 2020/4/6 8:48 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public boolean pauseSeat(FootprintDTO footprintDTO) {
        // 先判断状态是否为正常坐下
        if (footprintDTO.getStatus() == Footprint.STATUS.IN) {
            Footprint footprint = new Footprint();

            // 直接映射过去, 填充完整
            BeanUtils.copyProperties(footprintDTO, footprint);

            // 更新当前时间为暂停时间
            footprint.setUpdatedTime(Tools.getTimeStamp());

            // 状态修改为暂时离开
            footprint.setStatus(Footprint.STATUS.TEMP);

            // 跟新足迹内容
            this.update(footprint);

            return true;
        } else {
            // 状态不正确, 返回错误
            return false;
        }
    }

    /**
     * @Method pauseSeat
     * TODO: (新) 暂停座位方法
     * @param userId
     * @Return boolean
     * @Exception
     * @Date 2020/4/8 7:52 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public boolean pauseSeat(String userId) {
        // TODO: 构建 orm查询条件
        tk.mybatis.mapper.entity.Condition condition = new tk.mybatis.mapper.entity.Condition(Footprint.class);
        condition.createCriteria().andEqualTo("userId", userId).andEqualTo("status", Footprint.STATUS.IN);

        List<Footprint> list = this.findByCondition(condition);

        if (list.size() == 0) {
            return false;
        }

        Footprint footprint = list.get(0);

        // 更新当前时间为暂停时间
        footprint.setUpdatedTime(Tools.getTimeStamp());

        // FIXME: 增加已经自习时间
        int time = this.checkTime(userId);

        footprint.setStayTime(time);

        // 状态修改为暂时离开
        footprint.setStatus(Footprint.STATUS.TEMP);

        // 跟新足迹内容
        this.update(footprint);

        return true;
    }

    /**
     * @param user_id
     * @Method checkTime
     * TODO: 根据现在的用户, 检查现在状态为在坐的的数据, 返回已经使用的时间
     * @Return int
     * @Exception
     * @Date 2020/3/24 7:49 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public int checkTime(String user_id) {
        // 检查当前用户在坐的数据
        List<Footprint> footprintList = footprintMapper.checkTime(user_id, Footprint.STATUS.IN);

        // 用户第一次借座, 数组就为 0
        if (footprintList.size() == 0)
            return 0;

        Footprint footprint = footprintList.get(0);

        if (footprint == null)
            return 0;
        else {
            int time = 0;

            Timestamp current_time = Tools.getTimeStamp();

            // 如果没有记录已经坐下的时间, 证明他没有离开过, 直接算就可以了
            if (footprint.getStayTime() == 0) {

                // TODO: 如果 (当前时间 - 上次更新时间) < 选择自习时间, 返回 (当前时间 - 上次更新时间); 如果不是, 返回 最大自习时间 (因为已经用完自习时间了)
                time = Math.toIntExact((current_time.getTime() - footprint.getUpdatedTime().getTime())
                        < footprint.getWantedTime()
                        ? (current_time.getTime() - footprint.getUpdatedTime().getTime())
                        : footprint.getWantedTime());

                // 时间已经用完, 修改状态为离开座位
                if (time >= footprint.getWantedTime()) {
                    footprint.setUpdatedTime(current_time);
                    //究竟要不要更新自习时间? 要√
                    footprint.setStayTime(time);
                    // 用完时间, 更新选择的自习时间
                    footprint.setStatus(Footprint.STATUS.OUT);
                    this.update(footprint);
                }

                return time;
            } else {
                // 已经有坐下时间, 证明状态已经修改过

                time = Math.toIntExact((current_time.getTime() - footprint.getUpdatedTime().getTime())
                        < footprint.getWantedTime()
                        ? (current_time.getTime() - footprint.getUpdatedTime().getTime() + footprint.getStayTime())
                        : footprint.getStayTime() + footprint.getWantedTime());

                // 时间已经用完, 修改状态为离开座位
                if (time >= footprint.getStayTime() + footprint.getWantedTime()) {
                    footprint.setUpdatedTime(current_time);
                    // 究竟要不要更新自习时间? 要√
                    footprint.setStayTime(time);
                    // 用完时间, 更新选择的自习时间
                    footprint.setStatus(Footprint.STATUS.OUT);
                    this.update(footprint);
                }

                return time;
            }
        }
    }

    /**
     * @Method checkSeatStatus
     * TODO: 根据自习室编号, 座位编号, 寻找当前的座位信息
     * @param room_number
     * @param seats_number
     * @Return com.study.room.model.Footprint
     * @Exception
     * @Date 2020/4/5 8:36 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public Footprint checkSeatStatus(String room_number, String seats_number) {
        // TODO: 根据返回的前五列表, 抽取第一座位现阶段的状态返回

        // 获得前五列表
        List<Footprint> footprints = footprintMapper.checkSeatStatus(room_number, seats_number);

        // 如果该座位信息存在历史记录的
        if (footprints != null) {

            // 取第一条并返回
            Footprint footprint = footprints.get(0);

            return footprint;
        } else {
            // 如果不存在记录, 返回空值
            return null;
        }

    }

    /**
     * @Method findUseSeatByUserId
     * TODO: 根据用户 id信息, 返回正在用的座位信息
     * @param userId
     * @Return com.study.room.model.Footprint
     * @Exception
     * @Date 2020/4/11 7:37 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public Footprint findUseSeatByUserId(String userId) {
        // 检查在座的情况
        tk.mybatis.mapper.entity.Condition conditionStateIn = new tk.mybatis.mapper.entity.Condition(Footprint.class);
        Example.Criteria criteriaStateIn = conditionStateIn.createCriteria();
        criteriaStateIn.andEqualTo("userId", userId);
        criteriaStateIn.andEqualTo("status", Footprint.STATUS.IN);
        List<Footprint> footprintsStateIn = this.findByCondition(conditionStateIn);

        if (footprintsStateIn.size() != 0)
            return footprintsStateIn.get(0);

        // 检查暂离情况
        tk.mybatis.mapper.entity.Condition conditionStateTemp = new tk.mybatis.mapper.entity.Condition(Footprint.class);
        Example.Criteria criteriaStateTemp = conditionStateTemp.createCriteria();
        criteriaStateTemp.andEqualTo("userId", userId);
        criteriaStateTemp.andEqualTo("status", Footprint.STATUS.TEMP);
        List<Footprint> footprintsStateTemp = this.findByCondition(conditionStateTemp);

        if (footprintsStateTemp.size() != 0) {
            return footprintsStateTemp.get(0);
        }

        return null;
    }

    /**
     * @Method checkHistoryByUser
     * TODO: 根据用户 id信息, 返回前五条历史记录
     * @param userId
     * @Return java.util.List<com.study.room.model.Footprint>
     * @Exception
     * @Date 2020/4/11 7:37 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public List<Footprint> checkHistoryByUser(String userId) {
        List<Footprint> footprints = footprintMapper.checkHistoryByUser(userId);
        return footprints;
    }
}
