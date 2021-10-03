package com.zty.kdd.kdniao.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;

/**
 * http://www.kdniao.com/api-track
 */
public class KdniaoMaptrackQueryResponse extends ThirdMaptrackQueryResponse {

    /**
     * 必传
     * 用户 ID
     */
    private String EBusinessID;

    /**
     * 选传
     * 订单编号
     */
    private String OrderCode;

    /**
     * 必传
     * 快递公司编码
     */
    private String ShipperCode;

    /**
     * 必传
     * 快递单号
     */
    private String LogisticCode;

    /**
     * 必传
     * 是否成功
     */
    private boolean Success;

    /**
     * 选传
     * 失败原因
     */
    private String Reason;

    /**
     * 必传
     * 普通物流状态
     * 0-暂无轨迹信息
     * 1-已揽收
     * 2-在途中
     * 3-签收
     * 4-问题件
     */
    private String State;

    /**
     * 必传(付费版才有)
     * 增值物流状态：
     * 0-暂无轨迹信息
     * 1-已揽收
     * 2-在途中
     * 201-到达派件城市
     * 202-派件中
     * 211-已放入快递柜或驿站
     * 3-已签收
     * 301-正常签收
     * 302-派件异常后最终签收
     * 304-代收签收
     * 311-快递柜或驿站签收
     * 4-问题件
     * 401-发货无信息
     * 402-超时未签收
     * 403-超时未更新
     * 404-拒收(退件)
     * 405-派件异常
     * 406-退货签收
     * 407-退货未签收
     * 412-快递柜或驿站超时未取
     * 10-待揽件
     */
    private String StateEx;

    /**
     * 轨迹信息
     */
    private List<Trace> Traces;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
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

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStateEx() {
        return StateEx;
    }

    public void setStateEx(String stateEx) {
        StateEx = stateEx;
    }

    public List<Trace> getTraces() {
        return Traces;
    }

    public void setTraces(List<Trace> traces) {
        Traces = traces;
    }

    public void sortByTimeDesc() {
        this.Traces = this.Traces
                .stream()
                .sorted(Comparator.comparing(Trace::getLongAcceptTime).reversed())
                .collect(Collectors.toList());
    }

    public static class Trace {

        private static final DateTimeFormatter ACCEPT_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        /**
         * 必传
         * 时间
         * 2014/06/24 20:18:58
         */
        private String AcceptTime;

        /**
         * 必传
         * 描述
         * 顺丰速运 已收取快件
         */
        private String AcceptStation;

        /**
         * 选传
         * 备注
         */
        private String Remark;

        /**
         * 必传(付费版才有)
         * 增值物流状态：
         * 0-暂无轨迹信息
         * 1-已揽收
         * 2-在途中
         * 201-到达派件城市
         * 202-派件中
         * 211-已放入快递柜或驿站
         * 3-已签收
         * 301-正常签收
         * 302-派件异常后最终签收
         * 304-代收签收
         * 311-快递柜或驿站签收
         * 4-问题件
         * 401-发货无信息
         * 402-超时未签收
         * 403-超时未更新
         * 404-拒收(退件)
         * 405-派件异常
         * 406-退货签收
         * 407-退货未签收
         * 412-快递柜或驿站超时未取
         * 10-待揽件
         */
        private String Action;

        /**
         * 必传(付费版才有)
         * 当前所在城市
         */
        private String Location;

        public long getLongAcceptTime() {
            return LocalDateTime.parse(this.AcceptTime, ACCEPT_TIME_FORMAT).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String acceptTime) {
            AcceptTime = acceptTime;
        }

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String acceptStation) {
            AcceptStation = acceptStation;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getAction() {
            return Action;
        }

        public void setAction(String action) {
            Action = action;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }
    }
}
