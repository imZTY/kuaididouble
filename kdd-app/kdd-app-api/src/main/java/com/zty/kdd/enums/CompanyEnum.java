package com.zty.kdd.enums;

import com.zty.framework.exception.BusinessException;
import com.zty.framework.third.RetrofitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tianyi
 * @date 2021-02-28 22:28
 */
public enum CompanyEnum {


    SHUNFENG("SF", "顺丰速运", (byte)1, null),
    YOUSU("UCE", "优速快递", (byte)2, null),
    ZTO("ZTO", "中通快递", (byte)3, null),
    BEST("HTKY", "百世快递", (byte)4, null),
    YUANTONG("YTO", "圆通快递", (byte)5, null),
    DEBANG("DBL", "德邦快递", (byte)6, null),
    KDNIAO("Kdniao", "快递鸟", (byte)7, null),
    ;

    private static final Logger logger = LoggerFactory.getLogger(CompanyEnum.class);

    /**
     * 需是我们kdd对外定义的API KEY
     */
    private String apikey;

    private String name;

    private Byte code;

    // FIXME: 2021/2/28 如何使用泛型来管理
    private Class retrofitService;

    CompanyEnum(String apikey, String name, Byte code, Class retrofitService) {
        this.apikey = apikey;
        this.name = name;
        this.code = code;
        this.retrofitService = retrofitService;
    }

    /**
     * 获取活动类型枚举
     * @param apikey 活动类型
     * @param throwIfAbsent 若无法匹配，是否抛出异常
     * @date 2020/11/02 18:20:17
     * @return
     */
    public static CompanyEnum getByApikey(String apikey, boolean throwIfAbsent) {
        for (CompanyEnum type : CompanyEnum.values()) {
            if (type.getApikey().equals(apikey)) {
                return type;
            }
        }
        if (throwIfAbsent) {
            logger.error("无法识别传入的apikey，{}", apikey);
            throw new BusinessException("500", "枚举缺少"+apikey);
        }
        return null;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
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
