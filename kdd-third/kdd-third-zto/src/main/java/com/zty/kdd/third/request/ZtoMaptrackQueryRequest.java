package com.zty.kdd.third.request;

/**
 * 中通物流查询，定制化参数类
 */
public class ZtoMaptrackQueryRequest extends ThirdMaptrackQueryRequest {

    /**
     * 运单号，必传
     */
    private String billCode;

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }
}
