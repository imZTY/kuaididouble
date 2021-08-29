package com.zty.kdd.api;

import com.zty.kdd.request.MaptrackQueryRequest;
import com.zty.kdd.response.MaptrackQueryResponse;

/**
 * 地图轨迹API
 * @author tianyi
 * @date 2021-02-28 22:18
 */
public interface MaptrackApi {

    public MaptrackQueryResponse singleQuery(MaptrackQueryRequest reqAO) throws Exception;

}
