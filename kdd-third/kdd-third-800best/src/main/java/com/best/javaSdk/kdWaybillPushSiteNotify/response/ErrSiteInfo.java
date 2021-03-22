package com.best.javaSdk.kdWaybillPushSiteNotify.response;



public class ErrSiteInfo {
	private String errorCode;
	private String errorDescription;
	private String siteCode;
	private String billCode;

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

    public String getSiteCode()
    {
        return this.siteCode;
    }

    public void setSiteCode(String value)
    {
        this.siteCode = value;
    }

    public String getBillCode()
    {
        return this.billCode;
    }

    public void setBillCode(String value)
    {
        this.billCode = value;
    }


}
