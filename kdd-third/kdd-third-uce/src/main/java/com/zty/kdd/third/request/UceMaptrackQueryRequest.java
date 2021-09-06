package com.zty.kdd.third.request;

/**
 * 优速物流查询，定制化参数类
 */
public class UceMaptrackQueryRequest extends ThirdMaptrackQueryRequest {

    public UceMaptrackQueryRequest() {
        this.bill = new Bill();
    }

    private Bill bill;

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public void setBillCode(String billCode) {
        this.bill.setBillCode(billCode);
    }

    public class Bill {

        /**
         * 运单号，逗号分隔最多20个
         * 目前暂无批量查询场景
         */
        private String billCode;

        /**
         * 订单号，逗号分隔最多20个
         * 目前没用到
         */
        private String orderCode;

        /**
         * 查询类型
         * 1＝查询最后一条，0＝查询全部跟踪记录，默认为0
         */
        private String queryType = "1";

        public String getBillCode() {
            return billCode;
        }

        public void setBillCode(String billCode) {
            this.billCode = billCode;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getQueryType() {
            return queryType;
        }

        public void setQueryType(String queryType) {
            this.queryType = queryType;
        }
    }
}
