package com.zty.kdd.config;

import java.nio.charset.StandardCharsets;

import cn.hutool.core.net.URLEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 支付中心配置
 * @author: tianyi.zeng
 * @create: 22/12/2021 - 下午 8:59
 */
@Component
public class PayCenterConfig {

    /**
     * 支付宝网站支付，跳转url
     */
    @Value("${paycenter.alipay.webpay:/paycenter/alipay/pay/web}")
    private String alipayWebpay;

    public String getAlipayWebpayUrl(String amt,
                                     String orderId,
                                     String subject,
                                     String body,
                                     String returnUrl) {
        StringBuilder sb = new StringBuilder( );
        sb.append("&amt=" + URLEncoder.QUERY.encode(amt, StandardCharsets.UTF_8));
        sb.append("&orderId=" + URLEncoder.QUERY.encode(orderId, StandardCharsets.UTF_8));
        sb.append("&subject=" + URLEncoder.QUERY.encode(subject, StandardCharsets.UTF_8));
        sb.append("&body=" + URLEncoder.QUERY.encode(body, StandardCharsets.UTF_8));
        sb.append("&returnUrl=" + URLEncoder.QUERY.encode(returnUrl == null ? "" : returnUrl, StandardCharsets.UTF_8));
        return alipayWebpay + sb.toString().replaceFirst("&", "?");
    }

    public String getAlipayWebpay() {
        return alipayWebpay;
    }

    public void setAlipayWebpay(String alipayWebpay) {
        this.alipayWebpay = alipayWebpay;
    }
}
