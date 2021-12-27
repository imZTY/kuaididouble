package com.zty.kdd.DO;

import java.util.Date;

import com.zty.framework.dto.DataDTO;

public class ChargeInfoDO extends DataDTO {
    private Integer id;

    private String chargeName;

    private Integer chargeType;

    private Integer rights;

    /**
     * 收费币种
     */
    private String chargeCurr;

    /**
     * 收费价格(单位:分)
     */
    private Long chargePrice;

    /**
     * 售卖条数
     */
    private Long amount;

    /**
     * 规则描述
     */
    private String description;

    private Byte disabled;

    private Integer createBy;

    private Date createTime;

    private Date updateTime;

    private Integer fldN1;

    private Integer fldN2;

    private Long fldL1;

    private String fldS1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getRights() {
        return rights;
    }

    public void setRights(Integer rights) {
        this.rights = rights;
    }

    public String getChargeCurr() {
        return chargeCurr;
    }

    public void setChargeCurr(String chargeCurr) {
        this.chargeCurr = chargeCurr;
    }

    public Long getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(Long chargePrice) {
        this.chargePrice = chargePrice;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getDisabled() {
        return disabled;
    }

    public void setDisabled(Byte disabled) {
        this.disabled = disabled;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFldN1() {
        return fldN1;
    }

    public void setFldN1(Integer fldN1) {
        this.fldN1 = fldN1;
    }

    public Integer getFldN2() {
        return fldN2;
    }

    public void setFldN2(Integer fldN2) {
        this.fldN2 = fldN2;
    }

    public Long getFldL1() {
        return fldL1;
    }

    public void setFldL1(Long fldL1) {
        this.fldL1 = fldL1;
    }

    public String getFldS1() {
        return fldS1;
    }

    public void setFldS1(String fldS1) {
        this.fldS1 = fldS1;
    }
}