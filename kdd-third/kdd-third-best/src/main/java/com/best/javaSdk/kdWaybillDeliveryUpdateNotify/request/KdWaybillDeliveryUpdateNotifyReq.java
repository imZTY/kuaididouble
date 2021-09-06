package com.best.javaSdk.kdWaybillDeliveryUpdateNotify.request;

import com.best.javaSdk.kdWaybillDeliveryUpdateNotify.request.UpdatePrintFeedbackList;
import com.best.javaSdk.kdWaybillDeliveryUpdateNotify.request.Auth;
import java.util.List;
import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdWaybillDeliveryUpdateNotifyReq implements BaseRequest {
	private List<UpdatePrintFeedbackList> updatePrintFeedbackList;
	private Auth auth;

    public List<UpdatePrintFeedbackList>  getUpdatePrintFeedbackList()
    {
        return this.updatePrintFeedbackList;
    }

    public void setUpdatePrintFeedbackList(List<UpdatePrintFeedbackList>  value)
    {
        this.updatePrintFeedbackList = value;
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
        return "KD_WAYBILL_DELIVERY_UPDATE_NOTIFY";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdWaybillDeliveryUpdateNotify.response.KdWaybillDeliveryUpdateNotifyRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdWaybillDeliveryUpdateNotify.response.KdWaybillDeliveryUpdateNotifyRsp.class);

    }

}
