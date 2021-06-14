package com.zty.kdd.service;

import com.github.pagehelper.Page;

import com.zty.kdd.DO.OrderInfoDO;

/**
 * @author tianyi
 * @date 2021-04-18 12:08
 */
public interface OrderService {

    /**
     * 事务充值
     * @param pageDo
     * @return
     */
    public boolean chargeWithTransaction(OrderInfoDO pageDo) throws Exception;

    /**
     * 分页查询充值订单
     * @param pageDo
     * @return
     */
    public Page<OrderInfoDO> pageOrderListByPage(OrderInfoDO pageDo);

    /**
     * 检查并返回已存在的充值订单
     * @param orderInfoDO
     * @return 如果不存在，返回null
     */
    public OrderInfoDO checkAndGetCharge(OrderInfoDO orderInfoDO);

    /**
     * 检查并返回已存在的退款订单
     * @param orderInfoDO
     * @return 如果不存在，返回null
     */
    public OrderInfoDO checkAndGetRefund(OrderInfoDO orderInfoDO);
}
