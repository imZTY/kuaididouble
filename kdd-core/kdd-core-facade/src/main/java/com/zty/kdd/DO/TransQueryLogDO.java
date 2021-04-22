package com.zty.kdd.DO;

import java.util.Date;

import com.zty.framework.dto.DataDTO;

public class TransQueryLogDO extends DataDTO {
    private Integer id;

    private Integer accountId;

    private Byte thirdApiCompany;

    private String requestMsg;

    private String responseMsg;

    private Long costTime;

    private Byte isError;

    private String messageId;

    private String requestIp;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Byte getThirdApiCompany() {
        return thirdApiCompany;
    }

    public void setThirdApiCompany(Byte thirdApiCompany) {
        this.thirdApiCompany = thirdApiCompany;
    }

    public String getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public Byte getIsError() {
        return isError;
    }

    public void setIsError(Byte isError) {
        this.isError = isError;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}