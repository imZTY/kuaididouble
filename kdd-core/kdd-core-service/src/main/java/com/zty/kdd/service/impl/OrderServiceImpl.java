package com.zty.kdd.service.impl;

import java.util.Date;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zty.kdd.DO.AccountBalanceDO;
import com.zty.kdd.DO.OrderInfoDO;
import com.zty.kdd.DO.example.OrderInfoDOExample;
import com.zty.kdd.constant.OrderStatus;
import com.zty.kdd.dao.AccountBalanceDOMapper;
import com.zty.kdd.dao.OrderInfoDOMapper;
import com.zty.kdd.service.OrderService;

/**
 * @author tianyi
 * @date 2021-04-18 12:16
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderInfoDOMapper orderInfoDOMapper;

    @Autowired
    private AccountBalanceDOMapper accountBalanceDOMapper;

    /**
     * 事务充值
     * 先创建订单，再改余额
     * @param orderInfoDO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean chargeWithTransaction(OrderInfoDO orderInfoDO) throws Exception {
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
        AccountBalanceDO accountBalanceDO = accountBalanceDOMapper.selectByPrimaryKey(orderInfoDO.getAccountId());
        accountBalanceDO.setTotalBalance(accountBalanceDO.getTotalBalance() + orderInfoDO.getBalanceChange());
        accountBalanceDO.setAvailableBalance(accountBalanceDO.getAvailableBalance() + orderInfoDO.getBalanceChange());
        accountBalanceDO.setPreSalty(System.currentTimeMillis()+"");
        int rows  = accountBalanceDOMapper.updateByPrimaryKey(accountBalanceDO);
        if (rows != 1) {
            throw new Exception("充值异常，修改余额失败");
        }
        return true;
    }

    /**
     * 分页查询充值订单
     *
     * @param pageDo
     * @return
     */
    @Override
    public Page<OrderInfoDO> pageOrderListByPage(OrderInfoDO pageDo) {
        OrderInfoDOExample example = new OrderInfoDOExample();
        example.createCriteria()
                .andAccountIdEqualTo(pageDo.getAccountId())
                .andOrderTypeEqualTo(pageDo.getOrderType())
                .andOrderMethodEqualTo(pageDo.getOrderMethod())
                .andStatusEqualTo(pageDo.getStatus());
        PageHelper.startPage(pageDo.getPage(), pageDo.getRows());
        return (Page<OrderInfoDO>) orderInfoDOMapper.selectByExample(example);
    }
}
