package com.zty.kdd.service.impl;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zty.pay.DO.OrderInfoDO;
import com.zty.pay.DO.example.OrderInfoDOExample;
import com.zty.pay.constant.OrderStatus;
import com.zty.pay.constant.OrderType;
import com.zty.pay.dao.OrderInfoDOMapper;
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

    /**
     * 事务充值
     * 先创建订单，再改余额
     * @param orderInfoDO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean chargeWithTransaction(OrderInfoDO orderInfoDO) throws Exception {
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
        orderInfoDO.setStatus(OrderStatus.SUCCESS);
        orderInfoDO.setCreateTime(new Date());
        orderInfoDOMapper.insertSelective(orderInfoDO);
        // 修改余额
        AccountBalanceDO accountBalanceDO = balanceService.singleQuery(new AccountBalanceDO().accountId(orderInfoDO.getAccountId()));
        accountBalanceDO.setTotalBalance(accountBalanceDO.getTotalBalance() + orderInfoDO.getBalanceChange());
        accountBalanceDO.setAvailableBalance(accountBalanceDO.getAvailableBalance() + orderInfoDO.getBalanceChange());
        accountBalanceDO.setPreSalty(System.currentTimeMillis()+"");
        int rows  = balanceService.update(accountBalanceDO);
        if (rows != 1) {
            throw new Exception("充值异常，修改余额失败");
        }
        return true;
    }

}
