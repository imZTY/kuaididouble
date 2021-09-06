package com.zty.kdd.third.factory.impl;

import com.alibaba.fastjson.JSON;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.factory.ThirdMaptrackQueryRequestFactory;
import com.zty.kdd.third.request.UceMaptrackQueryRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UceMaptrackQueryRequestFactory extends ThirdMaptrackQueryRequestFactory {

    private final static Logger log = LoggerFactory.getLogger(UceMaptrackQueryRequestFactory.class);

    /**
     * 每家物流公司，都要自己实现前端入参的解析
     * 中通专属请求报文拼装
     * @param apiRequest
     */
    @Override
    public UceMaptrackQueryRequest parseThirdQueryRequest(QueryParamDTO apiRequest) {
        UceMaptrackQueryRequest request = new UceMaptrackQueryRequest();
        if (StringUtils.isBlank(apiRequest.getNum())) {
            log.warn("运单号不可为空: {}", JSON.toJSONString(apiRequest));
            throw new ParamCheckException("运单号不可为空");
        }
        request.setBillCode(apiRequest.getNum());
        return request;
    }
}
