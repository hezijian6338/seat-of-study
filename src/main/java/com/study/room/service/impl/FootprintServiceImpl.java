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


/**
 * Created by CodeGenerator on 2020/03/21.
 */
@Service
@Transactional
public class FootprintServiceImpl extends AbstractService<Footprint> implements FootprintService {
    @Resource
    private FootprintMapper footprintMapper;

    @Override
    public boolean haveSeat(FootprintDTO footprintDTO) {
        Footprint footprint = new Footprint();

        // 直接映射过去, 填充完整
        BeanUtils.copyProperties(footprintDTO, footprint);

        // 时间填充需要补充
        footprint.setCreatedTime(Tools.getTimeStamp());
        footprint.setUpdatedTime(Tools.getTimeStamp());

        this.update(footprint);

        return false;
    }

    @Override
    public boolean leaveSeat(FootprintDTO footprintDTO) {
        // FIXME: 需要添加一个方法, 根据状态和当前自习室编号座位号进行查询现有的座位足迹信息


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
            String staty_time = footprintDTO.getStayTime();
            // 计算当次自习时间
//            staty_time = (Tools.getTimeStamp() - )

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
}
