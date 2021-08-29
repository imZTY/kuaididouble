package com.zty.kdd.third.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * third模块标准出参
 * @author: tianyi.zeng
 * @create: 20/8/2021 - 上午 10:31
 */
public class ThirdMaptrackQueryResponse implements Serializable {

    private CommunicateResult communicateResult;

    private BusinessResult businessResult;

    /**
     * 判断是否通信成功
     */
    public boolean isCommunicateSuccess() {
        return this.communicateResult.getIsError() == 0;
    }

    /**
     * 判断是否业务成功
     */
    public boolean isBusinessSuccess() {
        return this.businessResult.isSuccess();
    }

    public CommunicateResult getCommunicationResult() {
        return communicateResult;
    }

    public void setCommuniecationResult(CommunicateResult communicateResult) {
        this.communicateResult = communicateResult;
    }

    public BusinessResult getBusinessResult() {
        return businessResult;
    }

    public void setBusinessResult(BusinessResult businessResult) {
        this.businessResult = businessResult;
    }

    /**
     * 通信结果类
     */
    public static class CommunicateResult {

        private byte isError;

        private String messageId;

        private String responseStr;

        public static CommunicateResult error(String responseStr) {
            return new CommunicateResult((byte) 1, "", responseStr);
        }

        public CommunicateResult(byte isError, String messageId, String responseStr) {
            this.isError = isError;
            this.messageId = messageId;
            this.responseStr = responseStr;
        }

        public byte getIsError() {
            return isError;
        }

        public void setIsError(byte isError) {
            this.isError = isError;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getResponseStr() {
            return responseStr;
        }

        public void setResponseStr(String responseStr) {
            this.responseStr = responseStr;
        }
    }

    /**
     * 业务结果类
     */
    public static class BusinessResult {
        /**
         * 是否业务成功
         */
        private boolean success;

        private String errorCode;

        private String errorMsg;

        /**
         * 第三方运输状态代码（当前的运输状态）
         */
        private String thirdStateCode;

        /**
         * 轨迹节点数据，倒序排列
         */
        private List<ThirdTrackDataDTO> thirdTrackDataList;

        public BusinessResult(boolean success, String errorCode, String errorMsg) {
            this.success = success;
            this.errorCode = errorCode;
            this.errorMsg = errorMsg;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getThirdStateCode() {
            return thirdStateCode;
        }

        public void setThirdStateCode(String thirdStateCode) {
            this.thirdStateCode = thirdStateCode;
        }

        public List<ThirdTrackDataDTO> getThirdTrackDataList() {
            return thirdTrackDataList;
        }

        public void setThirdTrackDataList(List<ThirdTrackDataDTO> thirdTrackDataList) {
            this.thirdTrackDataList = thirdTrackDataList;
        }
    }

    private static final DateTimeFormatter TRACK_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 轨迹数据类(第三方模块，标准出参)
     */
    public static class ThirdTrackDataDTO {
        /**
         * 物流节点描述
         * 例: "圆通合作点【喵站】快件已到达财富金街院里3号底商驿站,联系电话18744663997"
         */
        private String context;

        /**
         * 时间，原始格式
         * 例: "2020-11-30 13:19:56"
         */
        private String time;

        /**
         * 格式化后时间
         * 例: "2020-11-30 13:19:56"
         */
        private String ftime;

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setTime(long ms) {
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(ms/1000L, 0, ZoneOffset.ofHours(8));
            this.time = dateTime.format(TRACK_DATE_FORMAT);
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public void setFtime(long ms) {
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(ms/1000L, 0, ZoneOffset.ofHours(8));
            this.ftime = dateTime.format(TRACK_DATE_FORMAT);
        }
    }
}
