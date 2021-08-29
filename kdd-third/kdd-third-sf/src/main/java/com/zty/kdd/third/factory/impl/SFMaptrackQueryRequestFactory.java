package com.zty.kdd.third.factory.impl;

import com.alibaba.fastjson.JSON;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.request.SFMaptrackQueryRequest;
import com.zty.kdd.third.factory.ThirdMaptrackQueryRequestFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 顺丰物流查询，定制化参数对象工厂
 * @author: tianyi.zeng
 * @create: 18/8/2021 - 上午 11:57
 */
@Component
public class SFMaptrackQueryRequestFactory extends ThirdMaptrackQueryRequestFactory {

    private final static Logger log = LoggerFactory.getLogger(SFMaptrackQueryRequestFactory.class);

    /**
     * 顺丰物流查询，入参转换
     * @param apiRequest
     * @return
     */
    @Override
    public SFMaptrackQueryRequest parseThirdQueryRequest(QueryParamDTO apiRequest) {
        SFMaptrackQueryRequest sfMaptrackQueryRequest = new SFMaptrackQueryRequest();
        if (StringUtils.isBlank(apiRequest.getNum())
                || StringUtils.isBlank(apiRequest.getPhone())
                || apiRequest.getPhone().length() < 4) {
            log.warn("请求参数有误: {}", JSON.toJSONString(apiRequest));
            throw new ParamCheckException("请求参数有误:");
        }
        sfMaptrackQueryRequest.setTransOrderNo(apiRequest.getNum());
        sfMaptrackQueryRequest.setPhone(apiRequest.getPhone());
        return sfMaptrackQueryRequest;
    }
}
