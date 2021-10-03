package com.zty.kdd.kdniao.request;

import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;

public class KdniaoMaptrackQueryRequest extends ThirdMaptrackQueryRequest {

    /**
     * 必传
     * 请求内容需进行URL(utf-8)编码。请求内容JSON格式，须和DataType一致。
     */
    private String RequestData;

    /**
     * 必传
     * 商户ID，请在我的服务页面查看。
     */
    private String EBusinessID;

    /**
     * 必传
     * 请求指令类型：1002
     */
    private String RequestType;

    /**
     * 选传
     * 轨迹排序，0-升序，1-降序，默认 0-升序
     */
    private int Sort = 1;

    /**
     * 必传
     * 数据内容签名：把(请求内容(未编码)+AppKey)进行MD5加密，然后Base64编码，最后 进行URL(utf-8)编码。详细过程请查看Demo。
     */
    private String DataSign;

    /**
     * 请求、返回数据类型：DataType=2，请求、返回数据类型均为 JSON 格式；
     * 选传
     */
    private String DataType = "2";

    public String getRequestData() {
        return RequestData;
    }

    public void setRequestData(String requestData) {
        RequestData = requestData;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getRequestType() {
        return RequestType;
    }

    public void setRequestType(String requestType) {
        RequestType = requestType;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int sort) {
        Sort = sort;
    }

    public String getDataSign() {
        return DataSign;
    }

    public void setDataSign(String dataSign) {
        DataSign = dataSign;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public static class RequestData {

        public RequestData(String shipperCode, String logisticCode) {
            ShipperCode = shipperCode;
            LogisticCode = logisticCode;
        }

        /**
         * ShipperCode 为 SF 时必填，对应寄件人
         * /收件人手机号后四位；
         * ShipperCode 为其他快递时，可不填或保
         * 留字段，不可传值
         */
        private String CustomerName;

        /**
         * 选传
         * 订单编号
         */
        private String OrderCode;

        /**
         * 必传
         * 快递公司编码
         * 详细编码参考 https://www.kdniao.com/documents 《快递鸟接口支持快递公司编码.xlsx》
         */
        private String ShipperCode;


        /**
         * 必传
         * 物流单号
         */
        private String LogisticCode;

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }

        public String getOrderCode() {
            return OrderCode;
        }

        public void setOrderCode(String orderCode) {
            OrderCode = orderCode;
        }

        public String getShipperCode() {
            return ShipperCode;
        }

        public void setShipperCode(String shipperCode) {
            ShipperCode = shipperCode;
        }

        public String getLogisticCode() {
            return LogisticCode;
        }

        public void setLogisticCode(String logisticCode) {
            LogisticCode = logisticCode;
        }
    }
}
