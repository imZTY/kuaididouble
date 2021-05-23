package com.zty.kdd.ao;

import com.google.gson.Gson;

/**
 * @author tianyi
 * @date 2021-02-23 03:33
 */
public class MaptrackQueryReqAO extends BaseReqAO {

    /**
     * 查询参数
     */
    private QueryParam param;

    public QueryParam getParam() {
        return param;
    }

    public void setParam(QueryParam param) {
        this.param = param;
    }

    @Override
    public String getParamStr() {
        return new Gson().toJson(this.param);
    }

    public static class QueryParam{

        /**
         * 查询的快递公司的编码， 一律用小写字母
         */
        private String com;

        /**
         * 查询的快递单号， 单号的最大长度是32个字符
         */
        private String num;

        /**
         * 收、寄件人的电话号码（手机和固定电话均可，只能填写一个，顺丰单号必填，其他快递公司选填。如座机号码有分机号，分机号无需上传。）
         */
        private String phone;

        /**
         * 出发地信息，如：广东省深圳市南山区金蝶软件园
         */
        private String from;

        /**
         * 目的地信息，如：北京朝阳区国际金融大厦
         */
        private String to;

        /**
         * 返回格式：0：json格式（默认），1：xml，2：html，3：text
         */
        private String show;

        /**
         * 返回结果排序:desc降序（默认）,asc 升序
         */
        private String order;

        /**
         * 订单下单时间，格式为（yyyy-MM-dd HH:mm:ss）如：2020-12-16 12:59:59
         */
        private String orderTime;

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }
    }
}
