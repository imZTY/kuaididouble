package com.zty.kdd.third.resp;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author tianyi
 * @date 2021-05-05 23:56
 */
public class MsgDataDTO implements Serializable {
    @JSONField(name = "routeResps")
    private List<RouteRespsDTO> routeResps;

    public List<RouteRespsDTO> getRouteResps() {
        return routeResps;
    }

    public void setRouteResps(List<RouteRespsDTO> routeResps) {
        this.routeResps = routeResps;
    }

    public static class RouteRespsDTO {
        @JSONField(name = "mailNo")
        private String mailNo;
        @JSONField(name = "routes")
        private List<RoutesDTO> routes;

        public String getNewestOpCode() {
            if (routes.size() != 0) {
                return routes.get(routes.size() - 1).getOpCode();
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

        public static class RoutesDTO {
            @JSONField(name = "acceptAddress")
            private String acceptAddress;
            @JSONField(name = "acceptTime")
            private String acceptTime;
            @JSONField(name = "remark")
            private String remark;
            @JSONField(name = "opCode")
            private String opCode;

            public String getAcceptAddress() {
                return acceptAddress;
            }

            public void setAcceptAddress(String acceptAddress) {
                this.acceptAddress = acceptAddress;
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
}
