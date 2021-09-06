package com.best.javaSdk.kdWaybillRouteNotify.response;

import com.best.javaSdk.kdWaybillRouteNotify.response.EdiRouteInfoList;
import java.util.List;
import com.best.javaSdk.BaseResponse;


public class KdWaybillRouteNotifyRsp implements BaseResponse {
	private boolean result;
	private String errorCode;
	private String errorDescription;
	private List<EdiRouteInfoList> ediRouteInfoList;

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

    public List<EdiRouteInfoList>  getEdiRouteInfoList()
    {
        return this.ediRouteInfoList;
    }

    public void setEdiRouteInfoList(List<EdiRouteInfoList>  value)
    {
        this.ediRouteInfoList = value;
    }

}
