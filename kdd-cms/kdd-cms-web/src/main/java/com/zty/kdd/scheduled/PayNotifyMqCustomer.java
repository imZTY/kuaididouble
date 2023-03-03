package com.zty.kdd.scheduled;

import com.alibaba.fastjson.JSON;
import com.zty.kdd.service.KddOrderService;
import com.zty.kdd.third.constant.KddConstant;
import com.zty.pay.DO.OrderInfoDO;
import com.zty.pay.constant.OrderStatus;
import com.zty.pay.service.OrderMqService;
import com.zty.pay.service.impl.PayOrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 支付回调MQ消费者
 * @author: tianyi.zeng
 * @create: 2/4/2022 - 下午 10:27
 */
@Component
@Configurable
@EnableScheduling
@EnableAsync
public class PayNotifyMqCustomer {

    private static final Logger log = LoggerFactory.getLogger(PayNotifyMqCustomer.class);

    @Autowired
    private OrderMqService orderMqService;

    @Autowired
    private KddOrderService kddOrderService;

    @Scheduled(fixedDelay = 1000L, initialDelay = 20*1000L)
    public void orderChargeSchedule() throws InterruptedException {
        OrderInfoDO localOrder = orderMqService.popFromPayNotify(KddConstant.KDD_BUSINESS_CODE);
        if (localOrder != null) {
            log.info("[MQ消费]开始处理充值订单:{}", localOrder.getId());
            // 充值条件检查
            if (OrderStatus.SUCCESS == localOrder.getStatus()
                    && localOrder.getFldN3() == 0) {
                // 如果未充值，则执行充值，
                try {
                    boolean isOk = this.kddOrderService.chargeWithTransaction(localOrder, false);
                    log.info("充值结果:{}, 订单号:{}", isOk, localOrder.getId());
                } catch (Exception e) {
                    log.error("充值失败,订单信息:{}, ", JSON.toJSONString(localOrder), e);
                }
            }
        }
    }
}
