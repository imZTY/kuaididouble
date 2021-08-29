package com.zty.kdd.third.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @author tianyi
 * @date 2021-05-05 23:43
 */
public class SFMaptrackQueryResponse extends ThirdMaptrackQueryResponse {

    /**
     * API平台异常信息，选传
     */
    @JSONField(name = "apiErrorMsg")
    private String apiErrorMsg;

    /**
     * API响应唯一号UUID，必传
     */
    @JSONField(name = "apiResponseID")
    private String apiResponseID;

    /**
     * API平台结果代码，必传
     * A1000	统一接入平台校验成功，调用后端服务成功;
     * 注意：
     * 不代表后端业务处理成功，实际业务处理结果，
     * 需要查看响应属性apiResultData中的详细结果
     * A1001	必传参数不可为空
     * A1002	请求时效已过期
     * A1003	IP无效
     * A1004	无对应服务权限
     * A1005	流量受控
     * A1006	数字签名无效
     * A1007	重复请求
     * A1008	数据解密失败
     * A1009	目标服务异常或不可达
     * A1010	状态为沙箱测试
     * A1099	系统异常
     */
    @JSONField(name = "apiResultCode")
    private String apiResultCode;

    /**
     * 业务处理详细结果，选传
     */
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
        return parseObject(apiResultData, ResultData.class);
    }

    public void setApiResultData(String apiResultData) {
        this.apiResultData = apiResultData;
    }

    public static class ResultData {

        /**
         * 	true 请求成功，false 请求失败
         */
        @JSONField(name = "success")
        private Boolean success;

        /**
         * 错误编码，S0000成功
         */
        @JSONField(name = "errorCode")
        private String errorCode;

        /**
         * 错误描述
         */
        @JSONField(name = "errorMsg")
        private String errorMsg;

        /**
         * 返回的路由详细数据
         */
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

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
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
