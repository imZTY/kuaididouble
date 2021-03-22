package com.best.javaSdk.kdWaybillQueryNotify.response;

import com.best.javaSdk.BaseResponse;


public class KdWaybillQueryNotifyRsp implements BaseResponse {
	private boolean result;
	private String count;
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

    public String getCount()
    {
        return this.count;
    }

    public void setCount(String value)
    {
        this.count = value;
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
