package com.best.javaSdk.kdWaybillAddressAnomaly.response;

import com.best.javaSdk.BaseResponse;


public class KdWaybillAddressAnomalyRsp implements BaseResponse {
	private boolean result;
	private String errorDescription;
	private String errorCode;
	private boolean anomaly;
	private String anomalyReason;

    public boolean getResult()
    {
        return this.result;
    }

    public void setResult(boolean value)
    {
        this.result = value;
    }

    public String getErrorDescription()
    {
        return this.errorDescription;
    }

    public void setErrorDescription(String value)
    {
        this.errorDescription = value;
    }

    public String getErrorCode()
    {
        return this.errorCode;
    }

    public void setErrorCode(String value)
    {
        this.errorCode = value;
    }

    public boolean getAnomaly()
    {
        return this.anomaly;
    }

    public void setAnomaly(boolean value)
    {
        this.anomaly = value;
    }

    public String getAnomalyReason()
    {
        return this.anomalyReason;
    }

    public void setAnomalyReason(String value)
    {
        this.anomalyReason = value;
    }


}
