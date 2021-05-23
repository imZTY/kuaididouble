package com.zty.kdd.third.dto;

import java.io.Serializable;

/**
 * @author tianyi
 * @date 2021-05-05 23:11
 */
public class ThirdQueryDTO implements Serializable {

    /**
     * 运单号
     */
    private String transOrderNo;

    public ThirdQueryDTO(String transOrderNo) {
        this.transOrderNo = transOrderNo;
    }

    public String getTransOrderNo() {
        return transOrderNo;
    }

    public void setTransOrderNo(String transOrderNo) {
        this.transOrderNo = transOrderNo;
    }
}
