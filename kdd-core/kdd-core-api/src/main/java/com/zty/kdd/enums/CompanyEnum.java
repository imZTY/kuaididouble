package com.zty.kdd.enums;

import com.zty.framework.third.RetrofitService;
import com.zty.kdd.yunda.service.YundaRetrofitService;

/**
 * @author tianyi
 * @date 2021-02-28 22:28
 */
public enum CompanyEnum {
    YUNDA("", "", YundaRetrofitService.class),
    ;

    private String name;

    private String code;

    // FIXME: 2021/2/28 如何使用泛型来管理
    private Class retrofitService;

    CompanyEnum(String name, String code, Class retrofitService) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Class<RetrofitService> getRetrofitService() {
        return retrofitService;
    }

    public void setRetrofitService(Class<RetrofitService> retrofitService) {
        this.retrofitService = retrofitService;
    }
}
