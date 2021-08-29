package com.zty.kdd.third.resp;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author tianyi
 * @date 2021-05-05 23:56
 */
public class MsgDataDTO implements Serializable {
    /**
     * 顺丰运单号对应的路由详情（列表）
     */
    @JSONField(name = "routeResps")
    private List<RouteRespsDTO> routeResps;

    public List<RouteRespsDTO> getRouteResps() {
        return routeResps;
    }

    public void setRouteResps(List<RouteRespsDTO> routeResps) {
        this.routeResps = routeResps;
    }

    public static class RouteRespsDTO {
        /**
         * 顺丰运单号
         */
        @JSONField(name = "mailNo")
        private String mailNo;

        /**
         * 路由信息（列表）
         */
        @JSONField(name = "routes")
        private List<RoutesDTO> routes;

        /**
         * routes降序排序
         */
        private void sortRoutesDesc() {
            this.routes = this.routes
                    .stream()
                    .sorted(Comparator.comparing(RoutesDTO::getLongAccepTime).reversed())
                    .collect(Collectors.toList());
        }

        public String getNewestOpCode() {
            // 排序，确保 routes 是降序的
            sortRoutesDesc();
            if (this.routes.size() != 0) {
                return this.routes.get(0).getOpCode();
            } else {
                return null;
            }
        }

        public String getMailNo() {
            return mailNo;
        }

        public void setMailNo(String mailNo) {
            this.mailNo = mailNo;
        }

        public List<RoutesDTO> getRoutes() {
            return routes;
        }

        public void setRoutes(List<RoutesDTO> routes) {
            this.routes = routes;
        }

    }

    public static class RoutesDTO {
        /**
         * 路由节点发生的地点
         * 例：深圳
         */
        @JSONField(name = "acceptAddress")
        private String acceptAddress;

        /**
         * 路由节点发生的时间，
         * 格式：YYYY-MM-DD HH24:MM:SS，示例：2012-7-30 09:30:00
         */
        @JSONField(name = "acceptTime")
        private String acceptTime;

        private static final DateTimeFormatter ACCEPT_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        /**
         * 路由节点具体描述
         * 例：已派件、已签收
         */
        @JSONField(name = "remark")
        private String remark;

        /**
         * 路由节点操作码
         * 详细文档：https://qiao.sf-express.com/pages/developDoc/index.html?level2=949000
         * 例：50、80
         */
        @JSONField(name = "opCode")
        private String opCode;

        public String getAcceptAddress() {
            return acceptAddress;
        }

        public void setAcceptAddress(String acceptAddress) {
            this.acceptAddress = acceptAddress;
        }

        public long getLongAccepTime() {
            return LocalDateTime.parse(this.acceptTime, ACCEPT_TIME_FORMAT).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        }

        public String getAcceptTime() {
            return acceptTime;
        }

        public void setAcceptTime(String acceptTime) {
            this.acceptTime = acceptTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getOpCode() {
            return opCode;
        }

        public void setOpCode(String opCode) {
            this.opCode = opCode;
        }
    }
}
