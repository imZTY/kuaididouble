package com.best.javaSdk.kdScanRegisterNotify.response;

import com.best.javaSdk.BaseResponse;


public class KdScanRegisterNotifyRsp implements BaseResponse {
	private boolean result;
	private String remark;
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

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String value)
    {
        this.remark = value;
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
