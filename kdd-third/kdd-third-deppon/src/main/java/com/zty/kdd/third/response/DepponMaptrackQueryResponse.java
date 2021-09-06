package com.zty.kdd.third.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 德邦物流轨迹查询，响应类
 * 文档：http://dop.deppon.com/#/apiDocs/apiDetail/NEW_TRACE_QUERY
 */
public class DepponMaptrackQueryResponse extends ThirdMaptrackQueryResponse {

    private static final DateTimeFormatter TRACE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 请求成功标识
     * true or false
     */
    private String result;

    /**
     * 结果代码
     * 参见错误码描述
     */
    private String resultCode;

    /**
     * 错误原因
     */
    private String reason;

    /**
     * 唯一请求编码
     */
    private String uniquerRequestNumber;

    /**
     * 响应数据
     */
    private Response responseParam;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUniquerRequestNumber() {
        return uniquerRequestNumber;
    }

    public void setUniquerRequestNumber(String uniquerRequestNumber) {
        this.uniquerRequestNumber = uniquerRequestNumber;
    }

    public Response getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(Response responseParam) {
        this.responseParam = responseParam;
    }

    public static class Response {

        /**
         * 德邦运单号
         */
        private String tracking_number;

        /**
         * 轨迹列表
         */
        private List<Trace> trace_list;

        public void sortByTimeDesc() {
            if (trace_list == null) {
                return;
            }
            this.trace_list = this.trace_list
                    .stream()
                    .sorted(Comparator.comparing(Trace::getLongTime).reversed())
                    .collect(Collectors.toList());
        }

        public String getTracking_number() {
            return tracking_number;
        }

        public void setTracking_number(String tracking_number) {
            this.tracking_number = tracking_number;
        }

        public List<Trace> getTrace_list() {
            return trace_list;
        }

        public void setTrace_list(List<Trace> trace_list) {
            this.trace_list = trace_list;
        }
    }

    public static class Trace {

        /**
         * 操作城市
         */
        private String city;

        /**
         * 轨迹描述   货物跟踪内容
         */
        private String description;

        /**
         * 操作网点
         */
        private String site;

        /**
         * 订单状态
         * GOT：开单；
         * DEPARTURE：出站；
         * ARRIVAL：进站；
         * ADVANCE_DELIVERY ：预派送；
         * SENT_SCAN：派送；
         * ERROR：滞留,延时派送；
         * FAILED：客户拒签/运单作废；
         * SIGNED：签收；
         * BACK_SIGNED：退回件签收；
         * OPERATETRACK：转寄；
         * STA_INBOUND：快递员入柜；
         * STA_SIGN：用户提货（快递柜签收）；
         */
        private String status;

        /**
         * 操作时间  内容发生时间
         */
        private String time;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTime() {
            return time;
        }

        public long getLongTime() {
            return LocalDateTime.parse(this.time, TRACE_TIME_FORMAT).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
