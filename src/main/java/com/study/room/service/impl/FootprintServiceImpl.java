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
        footprint.setCreatedTime(Tools.getStringTimeStamp());
        footprint.setUpdatedTime(Tools.getStringTimeStamp());

        return false;
    }

    @Override
    public boolean leaveSeat(FootprintDTO footprint) {
        return false;
    }

    @Override
    public boolean pauseSeat(FootprintDTO footprint) {
        return false;
    }
}
