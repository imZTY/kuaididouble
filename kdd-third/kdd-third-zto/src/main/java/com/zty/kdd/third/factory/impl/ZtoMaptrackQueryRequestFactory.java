package com.zty.kdd.third.factory.impl;

import com.alibaba.fastjson.JSON;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.factory.ThirdMaptrackQueryRequestFactory;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import com.zty.kdd.third.request.ZtoMaptrackQueryRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: tianyi.zeng
 * @create: 29/8/2021 - 下午 4:18
 */
@Component
public class ZtoMaptrackQueryRequestFactory extends ThirdMaptrackQueryRequestFactory {

    private final static Logger log = LoggerFactory.getLogger(ZtoMaptrackQueryRequestFactory.class);

    /**
     * 每家物流公司，都要自己实现前端入参的解析
     * 中通专属请求报文拼装
     * @param apiRequest
     */
    @Override
    public ZtoMaptrackQueryRequest parseThirdQueryRequest(QueryParamDTO apiRequest) {
        ZtoMaptrackQueryRequest request = new ZtoMaptrackQueryRequest();
        if (StringUtils.isBlank(apiRequest.getNum())) {
            log.warn("请求参数有误: {}", JSON.toJSONString(apiRequest));
            throw new ParamCheckException("请求参数有误:");
        }
        request.setBillCode(apiRequest.getNum());
        return request;
    }
}
