package com.zty.kdd.yuantong.response;

import java.util.Map;

/**
 * @author: tianyi.zeng
 * @create: 29/8/2021 - 下午 1:15
 */
public class MaptrackResponse {
    private Map<String, Object> map;
    private String code;
    private boolean success;
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

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
