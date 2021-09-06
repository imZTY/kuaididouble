package com.best.javaSdk;

public interface BaseRequest {
    public String obtainServiceType();

    BaseResponse makeResponse(String rsp, String format);
}
