package com.zty.kdd.service;

import com.github.pagehelper.Page;
import com.zty.pay.DO.OrderInfoDO;


/**
 * kdd业务专属订单服务
 * @author tianyi
 * @date 2021-04-18 12:08
 */
public interface KddOrderService {

    /**
     * 事务充值
     * @param pageDo
     * @return
     */
    public boolean chargeWithTransaction(OrderInfoDO pageDo, boolean isNewOrder) throws Exception;

}
