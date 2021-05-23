package com.zty.kdd.enums;

/**
 * 通信状态枚举
 * @author tianyi
 * @date 2021-02-28 22:04
 */
public enum StatusCodeEnum {
    OK(200, "查询成功"),
    PARAMETER_ERROR(400, "参数权限问题"),
    QUERY_FAIL(500, "查询无结果，请隔段时间再查"),
    SERVER_ERROR(501, "服务器错误"),
    SERVER_BUSY(502, "服务器繁忙"),
    SIGNATURE_FAIL(503, "验证签名失败"),
    AUTHENTICATION_FAIL(601, "没该功能权限"),
    ;

    private Integer code;

    private String message;

    StatusCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getStringCode() {
        return String.valueOf(code);
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
