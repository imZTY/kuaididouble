package com.zty.kdd.DO;

import java.util.Date;

import com.zty.framework.dto.DataDTO;
import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;

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

    public static class Builder {

        private Byte thirdApiCompany;
        private Integer accountId;
        private String requestMsg;
        private Long costTime;
        private Date createTime;

        private ThirdMaptrackQueryResponse.CommunicateResult communicateResult;

        public TransQueryLogDO build() {
            TransQueryLogDO transQueryLogDO = new TransQueryLogDO();
            transQueryLogDO.setAccountId(accountId);
            transQueryLogDO.setThirdApiCompany(thirdApiCompany);
            transQueryLogDO.setRequestMsg(requestMsg);
            transQueryLogDO.setCostTime(costTime);
            transQueryLogDO.setCreateTime(createTime);
            if (communicateResult.getIsError() == 1) {
                // 通信异常
                transQueryLogDO.setIsError((byte) 1);
                transQueryLogDO.setMessageId(communicateResult.getMessageId());
                transQueryLogDO.setResponseMsg(communicateResult.getResponseStr());
            } else {
                // 成功
                transQueryLogDO.setIsError((byte) 0);
                transQueryLogDO.setMessageId(communicateResult.getMessageId());
                transQueryLogDO.setResponseMsg(communicateResult.getResponseStr());
            }
            return transQueryLogDO;
        }

        public Builder thirdApiCompany(Byte thirdApiCompany) {
            this.thirdApiCompany = thirdApiCompany;
            return this;
        }

        public Builder accountId(Integer accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder requestMsg(String requestMsg) {
            this.requestMsg = requestMsg;
            return this;
        }

        public Builder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder communicationResult(ThirdMaptrackQueryResponse.CommunicateResult communicateResult) {
            this.communicateResult = communicateResult;
            return this;
        }

        public Builder costTime(Long costTime) {
            this.costTime = costTime;
            return this;
        }
    }

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
        this.responseMsg = responseMsg.length() > 512 ? responseMsg.substring(0, 512) : responseMsg;
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