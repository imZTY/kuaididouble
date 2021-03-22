package com.best.javaSdk.kdCreateOrderNotify.request;

import com.best.javaSdk.kdCreateOrderNotify.request.Sender;
import com.best.javaSdk.kdCreateOrderNotify.request.Receiver;
import com.best.javaSdk.kdCreateOrderNotify.request.Items;
import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdCreateOrderNotifyReq implements BaseRequest {
	private String customerName;
	private String customerId;
	private String txLogisticID;
	private String tradeNo;
	private String mailNo;
	private String orderType;
	private String serviceType;
	private String orderFlag;
	private String sendStartTime;
	private String sendEndTime;
	private String recSite;
	private double goodsValue;
	private double itemsValue;
	private double insuranceValue;
	private int special;
	private String remark;
	private double totalServiceFee;
	private double buyServiceFee;
	private double codSplitFee;
	private Sender sender;
	private Receiver receiver;
	private Items items;
	private String cusSendCode;
	private double itemWeight;

    public String getCustomerName()
    {
        return this.customerName;
    }

    public void setCustomerName(String value)
    {
        this.customerName = value;
    }

    public String getCustomerId()
    {
        return this.customerId;
    }

    public void setCustomerId(String value)
    {
        this.customerId = value;
    }

    public String getTxLogisticID()
    {
        return this.txLogisticID;
    }

    public void setTxLogisticID(String value)
    {
        this.txLogisticID = value;
    }

    public String getTradeNo()
    {
        return this.tradeNo;
    }

    public void setTradeNo(String value)
    {
        this.tradeNo = value;
    }

    public String getMailNo()
    {
        return this.mailNo;
    }

    public void setMailNo(String value)
    {
        this.mailNo = value;
    }

    public String getOrderType()
    {
        return this.orderType;
    }

    public void setOrderType(String value)
    {
        this.orderType = value;
    }

    public String getServiceType()
    {
        return this.serviceType;
    }

    public void setServiceType(String value)
    {
        this.serviceType = value;
    }

    public String getOrderFlag()
    {
        return this.orderFlag;
    }

    public void setOrderFlag(String value)
    {
        this.orderFlag = value;
    }

    public String getSendStartTime()
    {
        return this.sendStartTime;
    }

    public void setSendStartTime(String value)
    {
        this.sendStartTime = value;
    }

    public String getSendEndTime()
    {
        return this.sendEndTime;
    }

    public void setSendEndTime(String value)
    {
        this.sendEndTime = value;
    }

    public String getRecSite()
    {
        return this.recSite;
    }

    public void setRecSite(String value)
    {
        this.recSite = value;
    }

    public double getGoodsValue()
    {
        return this.goodsValue;
    }

    public void setGoodsValue(double value)
    {
        this.goodsValue = value;
    }

    public double getItemsValue()
    {
        return this.itemsValue;
    }

    public void setItemsValue(double value)
    {
        this.itemsValue = value;
    }

    public double getInsuranceValue()
    {
        return this.insuranceValue;
    }

    public void setInsuranceValue(double value)
    {
        this.insuranceValue = value;
    }

    public int getSpecial()
    {
        return this.special;
    }

    public void setSpecial(int value)
    {
        this.special = value;
    }

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String value)
    {
        this.remark = value;
    }

    public double getTotalServiceFee()
    {
        return this.totalServiceFee;
    }

    public void setTotalServiceFee(double value)
    {
        this.totalServiceFee = value;
    }

    public double getBuyServiceFee()
    {
        return this.buyServiceFee;
    }

    public void setBuyServiceFee(double value)
    {
        this.buyServiceFee = value;
    }

    public double getCodSplitFee()
    {
        return this.codSplitFee;
    }

    public void setCodSplitFee(double value)
    {
        this.codSplitFee = value;
    }

    public Sender getSender()
    {
        return this.sender;
    }

    public void setSender(Sender value)
    {
        this.sender = value;
    }

    public Receiver getReceiver()
    {
        return this.receiver;
    }

    public void setReceiver(Receiver value)
    {
        this.receiver = value;
    }

    public Items getItems()
    {
        return this.items;
    }

    public void setItems(Items value)
    {
        this.items = value;
    }

    public String getCusSendCode()
    {
        return this.cusSendCode;
    }

    public void setCusSendCode(String value)
    {
        this.cusSendCode = value;
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
        return "KD_CREATE_ORDER_NOTIFY";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdCreateOrderNotify.response.KdCreateOrderNotifyRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdCreateOrderNotify.response.KdCreateOrderNotifyRsp.class);

    }

}
