package com.zty.kdd.third.request;

/**
 * 德邦物流轨迹查询，请求类
 */
public class DepponMaptrackQueryRequest extends ThirdMaptrackQueryRequest {

    /**
     * 德邦运单号，最大长度15，必传
     */
    private String mailNo;

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }
}
