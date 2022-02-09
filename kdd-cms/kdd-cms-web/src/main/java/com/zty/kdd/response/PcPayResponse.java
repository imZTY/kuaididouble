package com.zty.kdd.response;

/**
 * PC支付响应类
 * @author: tianyi.zeng
 * @create: 9/2/2022 - 下午 10:42
 */
public class PcPayResponse {

    /**
     * 本地订单号
     */
    private Long localOrderId;

    /**
     * 跳转至支付中心完成支付的地址，将用于 window.open(payCenterUrl);
     */
    private String payCenterUrl;

    public PcPayResponse(Long localOrderId, String payCenterUrl) {
        this.localOrderId = localOrderId;
        this.payCenterUrl = payCenterUrl;
    }

    public Long getLocalOrderId() {
        return localOrderId;
    }

    public void setLocalOrderId(Long localOrderId) {
        this.localOrderId = localOrderId;
    }

    public String getPayCenterUrl() {
        return payCenterUrl;
    }

    public void setPayCenterUrl(String payCenterUrl) {
        this.payCenterUrl = payCenterUrl;
    }
}
