package com.zty.kdd.third.factory;

import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;

/**
 * @author: tianyi.zeng
 * @create: 18/8/2021 - 上午 12:24
 */
public abstract class ThirdMaptrackQueryRequestFactory {

    /**
     * 每家物流公司，都要自己实现前端入参的解析
     * @param apiRequest
     */
    public abstract <T extends ThirdMaptrackQueryRequest> T parseThirdQueryRequest(QueryParamDTO apiRequest);

}
