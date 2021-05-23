package com.zty.kdd.ao;

import com.zty.framework.dto.DataDTO;

/**
 * @author tianyi
 * @date 2021-05-08 01:35
 */
public abstract class BaseReqAO extends DataDTO {

    /**
     * 授权码，请申请企业版获取
     */
    private String customer;

    /**
     * 签名， 用于验证身份， 按param + key + customer 的顺序进行MD5加密
     * （注意加密后字符串一定要转32位大写）， 不需要加上“+”号
     */
    private String sign;

    public abstract String getParamStr();

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
