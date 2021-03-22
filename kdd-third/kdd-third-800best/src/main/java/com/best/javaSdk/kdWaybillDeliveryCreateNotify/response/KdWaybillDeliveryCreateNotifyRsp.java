package com.best.javaSdk.kdWaybillDeliveryCreateNotify.response;

import com.best.javaSdk.BaseResponse;


public class KdWaybillDeliveryCreateNotifyRsp implements BaseResponse {
	private boolean result;
	private String errorCode;
	private String errorDescription;

    public boolean getResult()
    {
        return this.result;
    }

    public void setResult(boolean value)
    {
        this.result = value;
    }

    public String getErrorCode()
    {
        return this.errorCode;
    }

    public void setErrorCode(String value)
    {
        this.errorCode = value;
    }

    public String getErrorDescription()
    {
        return this.errorDescription;
    }

    public void setErrorDescription(String value)
    {
        this.errorDescription = value;
    }


}
