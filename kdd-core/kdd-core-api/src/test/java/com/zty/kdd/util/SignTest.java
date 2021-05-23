package com.zty.kdd.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * @author tianyi
 * @date 2021-05-24 01:54
 */
public class SignTest {

    @Test
    public void makeSign() {
        String param = "{\"com\":\"shunfeng\",\"num\":\"SF13092315379\",\"from\":\"北京市朝阳区\",\"phone\":\"18320444515\",\"to\":\"广东省深圳市南山区\",\"show\":\"0\",\"order\":\"desc\",\"orderTime\":\"\"}";
        String customer = "6389836417";
        String key = "BynwXeDjrezYZAs359HD";
        System.out.println(DigestUtils.md5Hex(param + key + customer).toUpperCase());
    }
}
