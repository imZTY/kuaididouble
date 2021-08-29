package com.zty.kdd.response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 物流轨迹查询，标准出参
 * @author tianyi
 * @date 2021-02-23 03:33
 */
public class MaptrackQueryResponse {

    /**
     * 描述信息
     */
    @JSONField(name = "message")
    private String message;

    /**
     * 物流状态
     */
    @JSONField(name = "state")
    private String state;

    /**
     * 通信状态
     */
    @JSONField(name = "status")
    private String status;

    /**
     * 快递单明细状态标记
     * TODO 快递100未实现
     */
    @JSONField(name = "condition")
    private String condition;

    /**
     * 是否签收标记
     * TODO 快递100未实现
     */
    @JSONField(name = "ischeck")
    private String ischeck;

    /**
     * 快递公司编码,一律用小写字母
     */
    @JSONField(name = "com")
    private String com;

    /**
     * 单号
     */
    @JSONField(name = "nu")
    private String nu;

    /**
     * 最新查询结果，数组，包含多项，全量，倒序（即时间最新的在最前），每项都是对象，对象包含字段请展开
     */
    @JSONField(name = "data")
    private List<TrackDataDTO> data;

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

    public List<TrackDataDTO> getData() {
        return data;
    }

    public void setData(List<TrackDataDTO> data) {
        this.data = data;
    }

    /**
     * 轨迹数据类(API模块，标准出参)
     */
    public static class TrackDataDTO {
        /**
         * 物流节点描述
         * 例: "圆通合作点【喵站】快件已到达财富金街院里3号底商驿站,联系电话18744663997"
         */
        @JSONField(name = "context")
        private String context;

        /**
         * 时间，原始格式
         * 例: "2020-11-30 13:19:56"
         */
        @JSONField(name = "time")
        private String time;

        /**
         * 格式化后时间
         * 例: "2020-11-30 13:19:56"
         */
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
