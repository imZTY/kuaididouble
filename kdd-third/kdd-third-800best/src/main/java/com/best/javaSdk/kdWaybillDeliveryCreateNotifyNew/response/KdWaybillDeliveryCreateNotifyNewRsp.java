package com.best.javaSdk.kdWaybillDeliveryCreateNotifyNew.response;

import com.best.javaSdk.kdWaybillDeliveryCreateNotifyNew.response.EDIPrintDetailList;
import java.util.List;
import com.best.javaSdk.BaseResponse;


public class KdWaybillDeliveryCreateNotifyNewRsp implements BaseResponse {
	private boolean result;
	private String errorCode;
	private String errorDescription;
	private List<EDIPrintDetailList> EDIPrintDetailList;

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

    public List<EDIPrintDetailList>  getEDIPrintDetailList()
    {
        return this.EDIPrintDetailList;
    }

    public void setEDIPrintDetailList(List<EDIPrintDetailList>  value)
    {
        this.EDIPrintDetailList = value;
    }

}
