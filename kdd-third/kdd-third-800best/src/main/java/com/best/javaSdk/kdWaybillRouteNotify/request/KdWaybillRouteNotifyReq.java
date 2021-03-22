package com.best.javaSdk.kdWaybillRouteNotify.request;

import com.best.javaSdk.kdWaybillRouteNotify.request.EdiRouteInfoList;
import com.best.javaSdk.kdWaybillRouteNotify.request.Auth;
import java.util.List;
import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdWaybillRouteNotifyReq implements BaseRequest {
	private List<EdiRouteInfoList> ediRouteInfoList;
	private Auth auth;

    public List<EdiRouteInfoList>  getEdiRouteInfoList()
    {
        return this.ediRouteInfoList;
    }

    public void setEdiRouteInfoList(List<EdiRouteInfoList>  value)
    {
        this.ediRouteInfoList = value;
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
        return "KD_WAYBILL_ROUTE_NOTIFY";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdWaybillRouteNotify.response.KdWaybillRouteNotifyRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdWaybillRouteNotify.response.KdWaybillRouteNotifyRsp.class);

    }

}
