package com.best.javaSdk.kdWaybillDeliveryCancelNotify.request;

import com.best.javaSdk.kdWaybillDeliveryCancelNotify.request.Auth;
import com.best.javaSdk.kdWaybillDeliveryCancelNotify.request.RemovePrintFeedbackList;
import java.util.List;
import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdWaybillDeliveryCancelNotifyReq implements BaseRequest {
	private Auth auth;
	private List<RemovePrintFeedbackList> removePrintFeedbackList;

    public Auth getAuth()
    {
        return this.auth;
    }

    public void setAuth(Auth value)
    {
        this.auth = value;
    }

    public List<RemovePrintFeedbackList>  getRemovePrintFeedbackList()
    {
        return this.removePrintFeedbackList;
    }

    public void setRemovePrintFeedbackList(List<RemovePrintFeedbackList>  value)
    {
        this.removePrintFeedbackList = value;
    }
    public String obtainServiceType() {
        return "KD_WAYBILL_DELIVERY_CANCEL_NOTIFY";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdWaybillDeliveryCancelNotify.response.KdWaybillDeliveryCancelNotifyRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdWaybillDeliveryCancelNotify.response.KdWaybillDeliveryCancelNotifyRsp.class);

    }

}
