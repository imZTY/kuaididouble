package com.zty.kdd.third.dto;

import java.io.Serializable;

import com.zty.kdd.ao.MaptrackQueryReqAO;

/**
 * @author tianyi
 * @date 2021-05-05 23:11
 */
public abstract class ThirdQueryDTO implements Serializable {

    /**
     * 运单号
     */
    private String transOrderNo;

    /**
     * 手机号
     */
    private String phone;

    public ThirdQueryDTO(MaptrackQueryReqAO.QueryParam apiRequest) {
        initData(apiRequest);
    }

    /**
     * 每家物流公司，都要自己实现前端入参的解析
     * @param apiRequest
     */
    public abstract void initData(MaptrackQueryReqAO.QueryParam apiRequest);

    public String getTransOrderNo() {
        return transOrderNo;
    }

    public void setTransOrderNo(String transOrderNo) {
        this.transOrderNo = transOrderNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
