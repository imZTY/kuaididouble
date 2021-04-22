package com.zty.kdd.DO;

import java.util.Date;

import com.zty.framework.dto.DataDTO;

public class SecretKeyInfoDO extends DataDTO {
    private Integer id;

    private String customerCode;

    private String secretKey;

    private Integer accountId;

    private Byte secretType;

    private Byte riskCycle;

    private Integer riskThreshold;

    private Byte disabled;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Byte getSecretType() {
        return secretType;
    }

    public void setSecretType(Byte secretType) {
        this.secretType = secretType;
    }

    public Byte getRiskCycle() {
        return riskCycle;
    }

    public void setRiskCycle(Byte riskCycle) {
        this.riskCycle = riskCycle;
    }

    public Integer getRiskThreshold() {
        return riskThreshold;
    }

    public void setRiskThreshold(Integer riskThreshold) {
        this.riskThreshold = riskThreshold;
    }

    public Byte getDisabled() {
        return disabled;
    }

    public void setDisabled(Byte disabled) {
        this.disabled = disabled;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}