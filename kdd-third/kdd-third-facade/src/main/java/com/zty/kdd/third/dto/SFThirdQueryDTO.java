package com.zty.kdd.third.dto;

import org.apache.commons.lang3.StringUtils;

import com.zty.kdd.ao.MaptrackQueryReqAO;

/**
 * @author tianyi
 * @date 2021-05-31 00:10
 */
public class SFThirdQueryDTO extends ThirdQueryDTO{

    public SFThirdQueryDTO(MaptrackQueryReqAO.QueryParam apiRequest) {
        super(apiRequest);
    }

    @Override
    public void initData(MaptrackQueryReqAO.QueryParam apiRequest) {
        if (StringUtils.isBlank(apiRequest.getNum())
                || StringUtils.isBlank(apiRequest.getPhone())
                || apiRequest.getPhone().length() < 4) {
            throw new IllegalArgumentException("请求参数有误:");
        }
        this.setTransOrderNo(apiRequest.getNum());
        this.setPhone(apiRequest.getPhone());
    }

}
