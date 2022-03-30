package com.zty.kdd.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zty.framework.exception.BusinessException;
import com.zty.pay.DO.OrderInfoDO;
import com.zty.pay.DO.example.OrderInfoDOExample;
import com.zty.pay.constant.OrderStatus;
import com.zty.pay.constant.OrderType;
import com.zty.pay.dao.OrderInfoDOMapper;
import com.zty.pay.service.PayOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zty.kdd.DO.AccountBalanceDO;
import com.zty.kdd.service.BalanceService;
import com.zty.kdd.service.KddOrderService;

/**
 * @author tianyi
 * @date 2021-04-18 12:16
 */
@Service
public class KddOrderServiceImpl implements KddOrderService {

    private static final Logger log = LoggerFactory.getLogger(KddOrderServiceImpl.class);

    @Autowired
    private OrderInfoDOMapper orderInfoDOMapper;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private PayOrderService payOrderService;

    /**
     * 事务充值
     * 先创建订单，再改余额
     * @param orderInfoDO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean chargeWithTransaction(OrderInfoDO orderInfoDO, boolean isNewOrder) throws Exception {
        if (orderInfoDO.getOrderAmount() <= 0) {
            log.error("参数错误，订单金额必须大于0，{}", orderInfoDO.getActualAmount());
            return false;
        }
        if (orderInfoDO.getActualAmount() <= 0) {
            log.error("参数错误，实际金额必须大于0，{}", orderInfoDO.getActualAmount());
            return false;
        }
        if (orderInfoDO.getBalanceChange() <= 0) {
            log.error("参数错误，余额变化必须大于0，{}", orderInfoDO.getBalanceChange());
            return false;
        }
        if (isNewOrder) {
            orderInfoDO.setStatus(OrderStatus.SUCCESS);
            orderInfoDO.setCreateTime(new Date());
            orderInfoDO.setFldN3((byte) 1);  //设置为 已充值
            String orderId = String.valueOf(payOrderService.createNewOrder(orderInfoDO));
            log.info("已生成本地订单号:{}", orderId);
        } else {
            if (orderInfoDO.getIdValue() == null) {
                log.error("订单号不存在,{}", JSON.toJSONString(orderInfoDO));
                return false;
            }
            orderInfoDO.setFldN3((byte) 1);  //设置为 已充值
            orderInfoDO.setUpdateTime(new Date());
            orderInfoDOMapper.updateByPrimaryKeySelective(orderInfoDO);
        }
        // 如果未说明产品，则使用默认产品
        AccountBalanceDO balanceQueryDO = new AccountBalanceDO().accountId(orderInfoDO.getAccountId());
        if (orderInfoDO.getFldN2() != null) {
            balanceQueryDO.setProductId(orderInfoDO.getFldN2());
        }
        // 修改余额
        AccountBalanceDO accountBalanceDO = balanceService.singleQuery(balanceQueryDO);
        accountBalanceDO.setTotalBalance(accountBalanceDO.getTotalBalance() + orderInfoDO.getBalanceChange());
        accountBalanceDO.setAvailableBalance(accountBalanceDO.getAvailableBalance() + orderInfoDO.getBalanceChange());
        accountBalanceDO.setPreSalty(System.currentTimeMillis()+"");
        int rows = balanceService.update(accountBalanceDO);
        if (rows != 1) {
            log.error("影响条数非1:{}", rows);
            throw new BusinessException("500", "充值异常，修改余额失败");
        }
        return true;
    }

}
