package com.zty.kdd.api;

import com.zty.kdd.ao.MaptrackQueryReqAO;
import com.zty.kdd.ao.MaptrackQueryRespAO;

/**
 * 地图轨迹API
 * @author tianyi
 * @date 2021-02-28 22:18
 */
public interface MaptrackApi {

    public MaptrackQueryRespAO query(MaptrackQueryReqAO reqAO);

}
