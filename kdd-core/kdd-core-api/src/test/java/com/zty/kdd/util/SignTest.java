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

    /**
     * 顺丰请求签名
     */
    @Test
    public void SF_makeSign() {
        String param = "{\"com\":\"shunfeng\",\"num\":\"SF110097497827\",\"from\":\"北京市朝阳区\",\"phone\":\"18320444515\",\"to\":\"广东省深圳市南山区\",\"show\":\"0\",\"order\":\"desc\",\"orderTime\":\"\"}";
        String customer = "6389836417";
        String key = "BynwXeDjrezYZAs359HD";
        String sign = DigestUtils.md5Hex(param + key + customer).toUpperCase();
        logger.info("param = {}, sign = {}", param, sign);
    }

    /**
     * 优速请求签名
     */
    @Test
    public void UCE_makeSign() {
        String param = "{\"com\":\"yousu\",\"num\":\"518270309160\",\"from\":\"北京市朝阳区\",\"phone\":\"18320444515\",\"to\":\"广东省深圳市南山区\",\"show\":\"0\",\"order\":\"desc\",\"orderTime\":\"\"}";
        String customer = "6389836417";
        String key = "BynwXeDjrezYZAs359HD";
        String sign = DigestUtils.md5Hex(param + key + customer).toUpperCase();
        logger.info("param = {}, sign = {}", param, sign);
    }

    /**
     * 百世请求签名
     */
    @Test
    public void BEST_makeSign() {
        String param = "{\"com\":\"baisi\",\"num\":\"557064806766534\",\"from\":\"北京市朝阳区\",\"phone\":\"18320444515\",\"to\":\"广东省深圳市南山区\",\"show\":\"0\",\"order\":\"desc\",\"orderTime\":\"\"}";
        String customer = "6389836417";
        String key = "BynwXeDjrezYZAs359HD";
        String sign = DigestUtils.md5Hex(param + key + customer).toUpperCase();
        logger.info("param = {}, sign = {}", param, sign);
    }

    /**
     * 德邦请求签名
     */
    @Test
    public void DBL_makeSign() {
        String param = "{\"com\":\"debang\",\"num\":\"DPK300105819031\",\"from\":\"北京市朝阳区\",\"phone\":\"18320444515\",\"to\":\"广东省深圳市南山区\",\"show\":\"0\",\"order\":\"desc\",\"orderTime\":\"\"}";
        String customer = "6389836417";
        String key = "BynwXeDjrezYZAs359HD";
        String sign = DigestUtils.md5Hex(param + key + customer).toUpperCase();
        logger.info("param = {}, sign = {}", param, sign);
    }

    /**
     * 圆通请求签名
     */
    @Test
    public void YTO_makeSign() {
        String param = "{\"com\":\"yuantong\",\"num\":\"YT5581926348919\",\"from\":\"北京市朝阳区\",\"phone\":\"18320444515\",\"to\":\"广东省深圳市南山区\",\"show\":\"0\",\"order\":\"desc\",\"orderTime\":\"\"}";
        String customer = "6389836417";
        String key = "BynwXeDjrezYZAs359HD";
        String sign = DigestUtils.md5Hex(param + key + customer).toUpperCase();
        logger.info("param = {}, sign = {}", param, sign);
    }
}
