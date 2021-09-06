package com.zty.kdd.third.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 轨迹查询，成功响应
 */
public class SuccessMaptrackResponse {

    /**
     * 轨迹
     */
    private List<Trace> traces;

    public SuccessMaptrackResponse(List<Trace> traces) {
        this.traces = traces;
    }

    public List<Trace> getTraces() {
        return traces;
    }

    public void setTraces(List<Trace> traces) {
        this.traces = traces;
    }

    public void sortByTimeDesc() {
        this.traces = this.traces
                .stream()
                .sorted(Comparator.comparing(Trace::getLongUpload_Time).reversed())
                .collect(Collectors.toList());
    }

    public static class Trace {

        private static final DateTimeFormatter UPDATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        /**
         * 运单号
         */
        private String waybill_No;

        /**
         * 走件产生时间 yyyy-MM-dd HH:mm:ss
         */
        private String upload_Time;

        /**
         * 	物流状态，固定为：
         * 	GOT 已收件;
         * 	ARRIVAL 已收入;
         * 	DEPARTURE 已发出;
         * 	PACKAGE 已打包;
         * 	SENT_SCAN 派件;
         * 	INBOUND 自提柜入柜;
         * 	SIGNED 签收成功;
         * 	FAILED 签收失败;
         * 	FORWARDING 转寄;
         * 	TMS_RETURN 退回;
         */
        private String infoContent;

        /**
         * 物流信息
         */
        private String processInfo;

        public String getWaybill_No() {
            return waybill_No;
        }

        public void setWaybill_No(String waybill_No) {
            this.waybill_No = waybill_No;
        }

        public String getUpload_Time() {
            return upload_Time;
        }

        public long getLongUpload_Time() {
            return LocalDateTime.parse(this.upload_Time, UPDATE_TIME_FORMAT).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        }

        public void setUpload_Time(String upload_Time) {
            this.upload_Time = upload_Time;
        }

        public String getInfoContent() {
            return infoContent;
        }

        public void setInfoContent(String infoContent) {
            this.infoContent = infoContent;
        }

        public String getProcessInfo() {
            return processInfo;
        }

        public void setProcessInfo(String processInfo) {
            this.processInfo = processInfo;
        }
    }
}
