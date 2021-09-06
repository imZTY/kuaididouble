package com.best.javaSdk.kdUpdateOrderNotify.request;

import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdUpdateOrderNotifyReq implements BaseRequest {
	private String txLogisticID;
	private String mailNo;
	private double itemWeight;

    public String getTxLogisticID()
    {
        return this.txLogisticID;
    }

    public void setTxLogisticID(String value)
    {
        this.txLogisticID = value;
    }

    public String getMailNo()
    {
        return this.mailNo;
    }

    public void setMailNo(String value)
    {
        this.mailNo = value;
    }

    public double getItemWeight()
    {
        return this.itemWeight;
    }

    public void setItemWeight(double value)
    {
        this.itemWeight = value;
    }

    public String obtainServiceType() {
        return "KD_UPDATE_ORDER_NOTIFY";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdUpdateOrderNotify.response.KdUpdateOrderNotifyRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdUpdateOrderNotify.response.KdUpdateOrderNotifyRsp.class);

    }

}
