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

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;


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
        // TODO: 完成基础逻辑

        Footprint footprint = new Footprint();

        // 直接映射过去, 填充完整
        BeanUtils.copyProperties(footprintDTO, footprint);

        // 时间填充需要补充 (√)
        footprint.setCreatedTime(Tools.getTimeStamp());
        footprint.setUpdatedTime(Tools.getTimeStamp());

        this.update(footprint);

        return false;
    }

    /**
     * @param footprintDTO
     * @Method leaveSeat
     * TODO: 离开正在使用的座位
     * @Return boolean
     * @Exception
     * @Date 2020/4/5 8:04 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public boolean leaveSeat(FootprintDTO footprintDTO) {
        // 需要添加一个方法, 根据状态和当前自习室编号座位号进行查询现有的座位足迹信息 (√)


        // 判断状态是否从正常坐下状态
        if (footprintDTO.getStatus() == Footprint.STATUS.IN) {
            Footprint footprint = new Footprint();

            // 直接映射过去, 填充完整
            BeanUtils.copyProperties(footprintDTO, footprint);

            // 更新当前时间为离开时间
            footprint.setUpdatedTime(Tools.getTimeStamp());

            // 状态修改为离开
            footprint.setStatus(Footprint.STATUS.OUT);

            // TODO: 填充一共总自习时间

            // 存在过暂离的情况, 自习时间本不为空
            int staty_time = footprintDTO.getStayTime();
            // 如果暂离不为空, 即是曾经暂离过
            if (staty_time != 0) {
                // 计算当次自习时间 (当前时间 - 上次更新时间 + 当前已有的时间)
                staty_time = (int) (Tools.getLongTimeStamp() - footprint.getUpdatedTime().getTime() + staty_time);
                footprint.setStayTime(staty_time);
            } else {
                // 没有进行过暂离 (自习时间为空), 直接当前时间 - 更新时间即可
                staty_time = (int) (Tools.getLongTimeStamp() - footprint.getUpdatedTime().getTime());
                footprint.setStayTime(staty_time);
            }

            // 跟新足迹内容
            this.update(footprint);
        }
        return false;
    }

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
     * @param user_id
     * @Method checkTime
     * TODO: 根据现在的用户, 检查现在状态为在坐的的数据
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
        Footprint footprint = footprintList.get(0);

        if (footprint == null)
            return 0;
        else {
            int time = 0;

            Timestamp current_time = Tools.getTimeStamp();

            // 如果没有记录已经坐下的时间, 证明他没有离开过, 直接算就可以了
            if (footprint.getStayTime() == 0) {

                time = (current_time.getNanos() - footprint.getUpdatedTime().getNanos()) > footprint.getWantedTime() ? (current_time.getNanos() - footprint.getUpdatedTime().getNanos()) : 0;

                // 时间已经用完, 修改状态为离开座位
                if (time == 0) {
                    footprint.setUpdatedTime(current_time);
                    footprint.setStatus(Footprint.STATUS.OUT);
                    this.update(footprint);
                }

                return time;
            } else {
                // 已经有坐下时间, 证明状态已经修改过

                time = (current_time.getNanos() - footprint.getUpdatedTime().getNanos() + footprint.getStayTime()) > footprint.getStayTime() ? (current_time.getNanos() - footprint.getUpdatedTime().getNanos()) : 0;

                // 时间已经用完, 修改状态为离开座位
                if (time == 0) {
                    footprint.setUpdatedTime(current_time);
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
}
