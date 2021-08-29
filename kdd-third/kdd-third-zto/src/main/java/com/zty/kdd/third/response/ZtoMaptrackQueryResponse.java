package com.zty.kdd.third.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * 中通轨迹查询响应
 * 文档地址：https://open.zto.com/#/interface?resourceGroup=20&apiName=zto.open.getRouteInfo
 */
public class ZtoMaptrackQueryResponse extends ThirdMaptrackQueryResponse{

    /**
     * 返回信息
     * 例：查询结果为空。
     */
    private String message;

    /**
     * 返回code
     * 例：0000、1001
     */
    private String statusCode;

    /**
     * 返回状态
     * 例：true
     */
    private boolean status;

    /**
     * 返回结果
     */
    private List<BillTrackOutput> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return getStatus();
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<BillTrackOutput> getResult() {
        return result;
    }

    public void setResult(List<BillTrackOutput> result) {
        this.result = result;
    }

    /**
     * 第一层 result 里的类，命名来自文档
     */
    public class BillTrackOutput {

        /**
         * 扩展字段
         * 例：
         */
        private String extend;

        /**
         * 快件所在国家
         * 例：China
         */
        private String country;

        /**
         * 签收人
         * 例：李四
         */
        private String signMan;

        /**
         * 操作人
         * 例：王五
         */
        private String operateUser;

        /**
         * 操作人联系方式
         * 例：18866666666
         */
        private String operateUserPhone;

        /**
         * 重量
         * 例：1000
         */
        private long weight;

        /**
         * 运单号
         * 例：73111390619708
         */
        private String billCode;

        /**
         *
         * 扫描类型:收件 、发件、 到件、 派件、 签收、退件、问题件、ARRIVAL （派件入三方自提柜等、SIGNED（派件出三方自提柜等）
         * 例：收件
         */
        private String scanType;

        /**
         * 操作人Code
         * 例：02100.10173
         */
        private String operateUserCode;

        /**
         * 上、下一站网点信息（scanType为发件，表示下一站；scanType为到件，表示上一站）
         * 例：
         */
        private TraceSiteInfoOutput preOrNextSite;

        /**
         * 扫描时间
         * 例：1609297452000
         */
        private long scanDate;

        /**
         * 扫描网点
         * 例：
         */
        private TraceSiteInfoOutput scanSite;

        /**
         * 轨迹描述
         * 例：【上海】（021-605511111） 的小吉（18888888888） 已揽收
         */
        private String desc;

        public String getExtend() {
            return extend;
        }

        public void setExtend(String extend) {
            this.extend = extend;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getSignMan() {
            return signMan;
        }

        public void setSignMan(String signMan) {
            this.signMan = signMan;
        }

        public String getOperateUser() {
            return operateUser;
        }

        public void setOperateUser(String operateUser) {
            this.operateUser = operateUser;
        }

        public String getOperateUserPhone() {
            return operateUserPhone;
        }

        public void setOperateUserPhone(String operateUserPhone) {
            this.operateUserPhone = operateUserPhone;
        }

        public long getWeight() {
            return weight;
        }

        public void setWeight(long weight) {
            this.weight = weight;
        }

        public String getBillCode() {
            return billCode;
        }

        public void setBillCode(String billCode) {
            this.billCode = billCode;
        }

        public String getScanType() {
            return scanType;
        }

        public void setScanType(String scanType) {
            this.scanType = scanType;
        }

        public String getOperateUserCode() {
            return operateUserCode;
        }

        public void setOperateUserCode(String operateUserCode) {
            this.operateUserCode = operateUserCode;
        }

        public TraceSiteInfoOutput getPreOrNextSite() {
            return preOrNextSite;
        }

        public void setPreOrNextSite(TraceSiteInfoOutput preOrNextSite) {
            this.preOrNextSite = preOrNextSite;
        }

        public long getScanDate() {
            return scanDate;
        }

        public void setScanDate(long scanDate) {
            this.scanDate = scanDate;
        }

        public TraceSiteInfoOutput getScanSite() {
            return scanSite;
        }

        public void setScanSite(TraceSiteInfoOutput scanSite) {
            this.scanSite = scanSite;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    /**
     * 站点信息类
     */
    public class TraceSiteInfoOutput {

        /**
         * 网点是否中心,T:true,F:false
         * 例：T
         */
        private String isCenter;

        /**
         * 网点编码/机柜hallCode;网点扫描时为网点编码，入站出站第三方签收时是机柜的hallCode
         * 例：31610
         */
        private String code;

        /**
         * 网点联系方式
         * 例：0316-7705555
         */
        private String phone;

        /**
         * 网点所在城市
         * 例：
         */
        private String city;

        /**
         * 网点是否转运中心.1，是 2，否
         * 例：1
         */
        private Integer isTransfer;

        /**
         * 网点名称
         * 例：廊坊
         */
        private String name;

        /**
         * 原始网点id,始终为网点id
         * 例：1850
         */
        private long siteId;

        /**
         * 网点扫描时为网点id，入站出站第三方签收时为null（即存代码，不确认是否有业务方在使用）
         * 例：1850
         */
        private long id;

        /**
         * 网点所在省份
         * 例：河北省
         */
        private String prov;

        public String getIsCenter() {
            return isCenter;
        }

        public void setIsCenter(String isCenter) {
            this.isCenter = isCenter;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Integer getIsTransfer() {
            return isTransfer;
        }

        public void setIsTransfer(Integer isTransfer) {
            this.isTransfer = isTransfer;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSiteId() {
            return siteId;
        }

        public void setSiteId(long siteId) {
            this.siteId = siteId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }
    }
}
