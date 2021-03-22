package com.best.javaSdk.kdWaybillDeliveryCreateNotify.request;

import com.best.javaSdk.kdWaybillDeliveryCreateNotify.request.Auth;
import com.best.javaSdk.kdWaybillDeliveryCreateNotify.request.CreatePrintFeedbackList;
import java.util.List;
import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdWaybillDeliveryCreateNotifyReq implements BaseRequest {
	private Auth auth;
	private List<CreatePrintFeedbackList> createPrintFeedbackList;

    public Auth getAuth()
    {
        return this.auth;
    }

    public void setAuth(Auth value)
    {
        this.auth = value;
    }

    public List<CreatePrintFeedbackList>  getCreatePrintFeedbackList()
    {
        return this.createPrintFeedbackList;
    }

    public void setCreatePrintFeedbackList(List<CreatePrintFeedbackList>  value)
    {
        this.createPrintFeedbackList = value;
    }
    public String obtainServiceType() {
        return "KD_WAYBILL_DELIVERY_CREATE_NOTIFY";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdWaybillDeliveryCreateNotify.response.KdWaybillDeliveryCreateNotifyRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdWaybillDeliveryCreateNotify.response.KdWaybillDeliveryCreateNotifyRsp.class);

    }

}
