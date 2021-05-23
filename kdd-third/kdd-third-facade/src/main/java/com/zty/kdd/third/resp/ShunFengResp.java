package com.zty.kdd.third.resp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author tianyi
 * @date 2021-05-05 23:43
 */
public class ShunFengResp {

    @JSONField(name = "apiErrorMsg")
    private String apiErrorMsg;
    @JSONField(name = "apiResponseID")
    private String apiResponseID;
    @JSONField(name = "apiResultCode")
    private String apiResultCode;
    @JSONField(name = "apiResultData")
    private String apiResultData;

    public String getApiErrorMsg() {
        return apiErrorMsg;
    }

    public void setApiErrorMsg(String apiErrorMsg) {
        this.apiErrorMsg = apiErrorMsg;
    }

    public String getApiResponseID() {
        return apiResponseID;
    }

    public void setApiResponseID(String apiResponseID) {
        this.apiResponseID = apiResponseID;
    }

    public String getApiResultCode() {
        return apiResultCode;
    }

    public void setApiResultCode(String apiResultCode) {
        this.apiResultCode = apiResultCode;
    }

    public ResultData getApiResultData() {
        return JSON.parseObject(apiResultData, ResultData.class);
    }

    public void setApiResultData(String apiResultData) {
        this.apiResultData = apiResultData;
    }

    public static class ResultData {

        @JSONField(name = "success")
        private Boolean success;
        @JSONField(name = "errorCode")
        private String errorCode;
        @JSONField(name = "errorMsg")
        private Object errorMsg;
        @JSONField(name = "msgData")
        private MsgDataDTO msgData;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public Object getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(Object errorMsg) {
            this.errorMsg = errorMsg;
        }

        public MsgDataDTO getMsgData() {
            return msgData;
        }

        public void setMsgData(MsgDataDTO msgData) {
            this.msgData = msgData;
        }
    }
}
