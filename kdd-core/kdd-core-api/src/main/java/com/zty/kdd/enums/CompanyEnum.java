package com.zty.kdd.enums;

import com.zty.framework.third.RetrofitService;

/**
 * @author tianyi
 * @date 2021-02-28 22:28
 */
public enum CompanyEnum {

    SHUNFENG("顺丰速运", (byte)1, null);

    private String name;

    private Byte code;

    // FIXME: 2021/2/28 如何使用泛型来管理
    private Class retrofitService;

    CompanyEnum(String name, Byte code, Class retrofitService) {
        this.name = name;
        this.code = code;
        this.retrofitService = retrofitService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public Class<RetrofitService> getRetrofitService() {
        return retrofitService;
    }

    public void setRetrofitService(Class<RetrofitService> retrofitService) {
        this.retrofitService = retrofitService;
    }
}
