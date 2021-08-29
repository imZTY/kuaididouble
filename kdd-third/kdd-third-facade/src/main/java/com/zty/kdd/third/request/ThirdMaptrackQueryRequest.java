package com.zty.kdd.third.request;

import java.io.Serializable;

/**
 * third模块标准入参
 * @author tianyi
 * @date 2021-05-05 23:11
 */
public class ThirdMaptrackQueryRequest implements Serializable {

    /**
     * 运单号
     */
    private String transOrderNo;

    /**
     * 手机号
     */
    private String phone;

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
