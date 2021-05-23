package com.zty.kdd.enums;

/**
 * 运单签收状态
 * @author tianyi
 * @date 2021-02-28 21:33
 */
public enum TransStatusEnum {
    ON_THE_WAY(0, "在途"),
    COLLECT(1, "揽件"),
    PUZZLE(2, "疑难"),
    RECEIVED(3, "签收"),
    REJECT_FINISH(4, "退签(退回并签收)"),
    DISTRIBUTE(5, "派件"),
    REJECT_ON_WAY(6, "退回(退运中)"),
    SWITCHING(7, "转投"),
    WAIT_CLEAR(10, "待清关"),
    CLEARING(11, "清关中"),
    CLEARED(12, "已清关"),
    CLEAR_ERROR(13, "清关异常"),
    REJECT(14, "拒签"),
    UNKNOW(-1, "未知")
    ;

    private Integer value;

    private String name;

    TransStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }
    public String getStringValue() {
        return String.valueOf(value);
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
