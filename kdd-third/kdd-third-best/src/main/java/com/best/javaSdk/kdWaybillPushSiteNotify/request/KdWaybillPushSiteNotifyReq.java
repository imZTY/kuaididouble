package com.best.javaSdk.kdWaybillPushSiteNotify.request;

import com.best.javaSdk.kdWaybillPushSiteNotify.request.SiteInfos;
import com.best.javaSdk.kdWaybillPushSiteNotify.request.Auth;
import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdWaybillPushSiteNotifyReq implements BaseRequest {
	private SiteInfos siteInfos;
	private Auth auth;

    public SiteInfos getSiteInfos()
    {
        return this.siteInfos;
    }

    public void setSiteInfos(SiteInfos value)
    {
        this.siteInfos = value;
    }

    public Auth getAuth()
    {
        return this.auth;
    }

    public void setAuth(Auth value)
    {
        this.auth = value;
    }

    public String obtainServiceType() {
        return "KD_WAYBILL_PUSH_SITE_NOTIFY";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdWaybillPushSiteNotify.response.KdWaybillPushSiteNotifyRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdWaybillPushSiteNotify.response.KdWaybillPushSiteNotifyRsp.class);

    }

}
