package com.best.javaSdk.kdWaybillPushSiteNotify.response;

import com.best.javaSdk.kdWaybillPushSiteNotify.response.ErrSiteInfos;
import com.best.javaSdk.BaseResponse;


public class KdWaybillPushSiteNotifyRsp implements BaseResponse {
	private boolean result;
	private ErrSiteInfos errSiteInfos;

    public boolean getResult()
    {
        return this.result;
    }

    public void setResult(boolean value)
    {
        this.result = value;
    }

    public ErrSiteInfos getErrSiteInfos()
    {
        return this.errSiteInfos;
    }

    public void setErrSiteInfos(ErrSiteInfos value)
    {
        this.errSiteInfos = value;
    }


}
