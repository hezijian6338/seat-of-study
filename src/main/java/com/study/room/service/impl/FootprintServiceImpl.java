package com.study.room.service.impl;

import com.study.room.dao.FootprintMapper;
import com.study.room.model.Footprint;
import com.study.room.service.FootprintService;
import com.study.room.core.AbstractService;
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

}
