package com.zty.kdd.ao;

import java.util.List;

/**
 * @author tianyi
 * @date 2021-02-23 03:33
 */
public class MaptrackQueryRespAO {
    
    private String message;

    private Integer state;

    private Integer status;

    private String condition;

    private Integer isCheck;

    private String com;

    private String nu;

    private String trailUrl;

    private String arrivalTime;

    private String totalTime;

    private String remainTime;

    private Boolean isLoop;

    /**
     * 路由信息：起始、当前、终点.
     */
    private RouteInfo routeInfo;

    private List<QueryResultInfo> data;

    private static class RouteInfo {

        private RouteInfoDetail from;

        private RouteInfoDetail cur;

        private RouteInfoDetail to;

        private class RouteInfoDetail {
            private String number;

            private String name;

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    private static class QueryResultInfo {
        private String context;

        private String time;

        private String ftime;

        private String status;

        private String areaCode;

        private String areaName;

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getTrailUrl() {
        return trailUrl;
    }

    public void setTrailUrl(String trailUrl) {
        this.trailUrl = trailUrl;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(String remainTime) {
        this.remainTime = remainTime;
    }

    public Boolean getLoop() {
        return isLoop;
    }

    public void setLoop(Boolean loop) {
        isLoop = loop;
    }

    public RouteInfo getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(RouteInfo routeInfo) {
        this.routeInfo = routeInfo;
    }

    public List<QueryResultInfo> getData() {
        return data;
    }

    public void setData(List<QueryResultInfo> data) {
        this.data = data;
    }
}
