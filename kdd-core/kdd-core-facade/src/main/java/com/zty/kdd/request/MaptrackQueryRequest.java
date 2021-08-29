package com.zty.kdd.request;

import com.alibaba.fastjson.JSON;
import com.zty.kdd.third.dto.QueryParamDTO;

/**
 * 物流轨迹查询，标准入参
 * @author tianyi
 * @date 2021-02-23 03:33
 */
public class MaptrackQueryRequest extends BaseRequest {

    private String kddCompanyCode;

    /**
     * 查询参数
     */
    private QueryParamDTO paramObj;

    public String getKddCompanyCode() {
        return kddCompanyCode;
    }

    public void setKddCompanyCode(String kddCompanyCode) {
        this.kddCompanyCode = kddCompanyCode;
    }

    public QueryParamDTO getParamObj() {
        return paramObj;
    }

    @Override
    public void setParamObj(String paramJson) {
        this.paramObj = JSON.parseObject(paramJson, QueryParamDTO.class);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
