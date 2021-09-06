package com.zty.kdd.third.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优速轨迹查询响应
 * 文档地址：https://api.uce.cn/doku.php?id=uop:track:query_track
 */
public class UceMaptrackQueryResponse extends ThirdMaptrackQueryResponse {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    /**
     * 响应结构类
     */
    public static class Response {
        /**
         * 返回T成功，返回F则失败
         */
        private String isSuccess;

        /**
         * 系统错误
         * 编号	说明
         * S0001	请求参数不正确
         * S0002	数据签名错误
         * S0003	非法的服务名称
         * S0004	非法的数据类型
         * S0005	非法的合作伙伴
         * S0006	非法的请求方法
         * S9998	非法的请求方法，只有用POST提交
         * S9999	系统异常，请重试
         * 业务错误
         * 编号	说明
         * B0101	产品服务未订购
         * B0102	订单不能更新
         * B0103	订单已经存在
         * B0104	订单不存在
         * B0107	字段不能为空，字符超长
         */
        private String errorCode;

        private List<TraceList> traceList;

        public String getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(String isSuccess) {
            this.isSuccess = isSuccess;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public List<TraceList> getTraceList() {
            return traceList;
        }

        public void setTraceList(List<TraceList> traceList) {
            this.traceList = traceList;
        }
    }

    /**
     * 节点列表数据类
     */
    public static class TraceList {

        private List<Trace> trace;

        public List<Trace> getTrace() {
            return trace;
        }

        public void setTrace(List<Trace> trace) {
            this.trace = trace;
        }

        public void sortByTimeDesc() {
            this.trace = this.trace
                    .stream()
                    .sorted(Comparator.comparing(Trace::getLongOptTime).reversed())
                    .collect(Collectors.toList());
        }
    }

    /**
     * 单个轨迹的节点数据类
     */
    public static class Trace {

        private static final DateTimeFormatter OP_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        /**
         * 运单编号	String(32)
         * 例：66824545122
         */
        private String billCode;

        /**
         * 订单编号	String(64)
         * 例：Taobao276759533
         */
        private String orderCode;

        /**
         * 	操作时间	Date
         * 	例：2012-12-16 12:22:20
         */
        private String optTime;

        /**
         * 操作网点	String(32)
         * 虎门网点
         */
        private String operateSite;

        /**
         * 跟踪记录	String(256)
         * 例：
         * [虎门]的【李元】正在派件, 电话：15846565466
         * 快件已签收，签收人是【圣达菲】
         */
        private String desc;

        /**
         * 操作类型	String(32)
         * 编号	说明
         * GET	收件
         * ARRIVAL	到件
         * DEPARTURE	发件
         * SENT_SCAN	派件
         * SIGN	签收
         * TO	转件
         * OTHER	其他
         */
        private String optType;

        /**
         * 联系人
         */
        private String contacts;

        /**
         * 联系电话
         */
        private String tel;

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

        public String getOptTime() {
            return optTime;
        }

        public long getLongOptTime() {
            return LocalDateTime.parse(this.optTime, OP_TIME_FORMAT).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        }

        public void setOptTime(String optTime) {
            this.optTime = optTime;
        }

        public String getOperateSite() {
            return operateSite;
        }

        public void setOperateSite(String operateSite) {
            this.operateSite = operateSite;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getOptType() {
            return optType;
        }

        public void setOptType(String optType) {
            this.optType = optType;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}
