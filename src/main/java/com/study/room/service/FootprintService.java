package com.study.room.service;
import com.study.room.dto.FootprintDTO;
import com.study.room.model.Footprint;
import com.study.room.core.Service;


/**
 * Created by CodeGenerator on 2020/03/21.
 */
public interface FootprintService extends Service<Footprint> {

    /**
     * @Method haveSeat
     * TODO: 扫描二维码坐下记录
     * @param footprint
     * @Return boolean
     * @Exception
     * @Date 2020/3/21 11:31 AM
     * @Author hezijian6338
     * @Version  1.0
     */
    boolean haveSeat(FootprintDTO footprint);

    /**
     * @Method leaveSeat
     * TODO: 离开座位记录
     * @param footprint
     * @Return boolean
     * @Exception
     * @Date 2020/3/21 11:52 AM
     * @Author hezijian6338
     * @Version  1.0
     */
    boolean leaveSeat(FootprintDTO footprint);

    /**
     * @Method pauseSeat
     * TODO: 暂时离开座位记录
     * @param footprint
     * @Return boolean
     * @Exception
     * @Date 2020/3/21 11:53 AM
     * @Author hezijian6338
     * @Version  1.0
     */
    boolean pauseSeat(FootprintDTO footprint);
}
