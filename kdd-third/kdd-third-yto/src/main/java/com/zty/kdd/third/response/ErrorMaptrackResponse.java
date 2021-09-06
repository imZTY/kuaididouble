package com.zty.kdd.third.response;

import java.util.Map;

/**
 * 轨迹查询，异常响应
 */
public class ErrorMaptrackResponse {
    /**
     * {"YT2600227881409":[]}
     */
    private Map<String, Object> map;
    /**
     * 1001
     */
    private String code;

    private boolean success;

    /**
     * 请求失败，请稍后重试~~
     */
    private String reason;
    /**
     * 查询结果为空。
     */
    private String message;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = "true".equalsIgnoreCase(success);
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
