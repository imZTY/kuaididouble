package com.zty.kdd.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tianyi
 * @date 2021-05-24 01:54
 */
public class SignTest {

    private static final Logger logger = LoggerFactory.getLogger(SignTest.class);

    @Test
    public void makeSign() {
        String param = "{\"com\":\"shunfeng\",\"num\":\"SF1100974978275\",\"from\":\"北京市朝阳区\",\"phone\":\"18320444515\",\"to\":\"广东省深圳市南山区\",\"show\":\"0\",\"order\":\"desc\",\"orderTime\":\"\"}";
        String customer = "6389836417";
        String key = "BynwXeDjrezYZAs359HD";
        String sign = DigestUtils.md5Hex(param + key + customer).toUpperCase();
        logger.info("param = {}, sign = {}", param, sign);
    }
}
