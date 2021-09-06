package com.best.javaSdk.kdWaybillDeliveryCreateNotifyNew.request;

import com.best.javaSdk.kdWaybillDeliveryCreateNotifyNew.request.Auth;
import com.best.javaSdk.kdWaybillDeliveryCreateNotifyNew.request.EDIPrintDetailList;
import java.util.List;
import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdWaybillDeliveryCreateNotifyNewReq implements BaseRequest {
	private Auth auth;
	private List<EDIPrintDetailList> EDIPrintDetailList;

    public Auth getAuth()
    {
        return this.auth;
    }

    public void setAuth(Auth value)
    {
        this.auth = value;
    }

    public List<EDIPrintDetailList>  getEDIPrintDetailList()
    {
        return this.EDIPrintDetailList;
    }

    public void setEDIPrintDetailList(List<EDIPrintDetailList>  value)
    {
        this.EDIPrintDetailList = value;
    }
    public String obtainServiceType() {
        return "KD_WAYBILL_DELIVERY_CREATE_NOTIFY_NEW";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdWaybillDeliveryCreateNotifyNew.response.KdWaybillDeliveryCreateNotifyNewRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdWaybillDeliveryCreateNotifyNew.response.KdWaybillDeliveryCreateNotifyNewRsp.class);

    }

}
