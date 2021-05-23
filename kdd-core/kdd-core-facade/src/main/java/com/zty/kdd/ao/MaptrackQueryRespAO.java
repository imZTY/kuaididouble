package com.zty.kdd.ao;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author tianyi
 * @date 2021-02-23 03:33
 */
public class MaptrackQueryRespAO {

    @JSONField(name = "message")
    private String message;
    @JSONField(name = "state")
    private String state;
    @JSONField(name = "status")
    private String status;
    @JSONField(name = "condition")
    private String condition;
    @JSONField(name = "ischeck")
    private String ischeck;
    @JSONField(name = "com")
    private String com;
    @JSONField(name = "nu")
    private String nu;
    @JSONField(name = "data")
    private List<DataDTO> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
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

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        @JSONField(name = "context")
        private String context;
        @JSONField(name = "time")
        private String time;
        @JSONField(name = "ftime")
        private String ftime;

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
    }
}
