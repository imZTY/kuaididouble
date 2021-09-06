package com.best.javaSdk.kdCancelOrderNotify.request;

import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdCancelOrderNotifyReq implements BaseRequest {
	private String txLogisticID;
	private String reason;

    public String getTxLogisticID()
    {
        return this.txLogisticID;
    }

    public void setTxLogisticID(String value)
    {
        this.txLogisticID = value;
    }

    public String getReason()
    {
        return this.reason;
    }

    public void setReason(String value)
    {
        this.reason = value;
    }

    public String obtainServiceType() {
        return "KD_CANCEL_ORDER_NOTIFY";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdCancelOrderNotify.response.KdCancelOrderNotifyRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdCancelOrderNotify.response.KdCancelOrderNotifyRsp.class);

    }

}
