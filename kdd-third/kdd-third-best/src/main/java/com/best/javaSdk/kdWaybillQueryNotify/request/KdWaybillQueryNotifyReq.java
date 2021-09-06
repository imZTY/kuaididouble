package com.best.javaSdk.kdWaybillQueryNotify.request;

import com.best.javaSdk.kdWaybillQueryNotify.request.Auth;
import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdWaybillQueryNotifyReq implements BaseRequest {
	private String printSite;
	private String printMan;
	private Auth auth;

    public String getPrintSite()
    {
        return this.printSite;
    }

    public void setPrintSite(String value)
    {
        this.printSite = value;
    }

    public String getPrintMan()
    {
        return this.printMan;
    }

    public void setPrintMan(String value)
    {
        this.printMan = value;
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
        return "KD_WAYBILL_QUERY_NOTIFY";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdWaybillQueryNotify.response.KdWaybillQueryNotifyRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdWaybillQueryNotify.response.KdWaybillQueryNotifyRsp.class);

    }

}
