package com.zty.kdd.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zty.kdd.DO.AccountBalanceDO;
import com.zty.kdd.dao.AccountBalanceDOMapper;
import com.zty.kdd.service.BalanceService;

/**
 * @author tianyi
 * @date 2021-04-18 12:15
 */
@Service
public class BalanceServiceImpl implements BalanceService {

    private static final Logger log = LoggerFactory.getLogger(BalanceServiceImpl.class);

    @Autowired
    private AccountBalanceDOMapper accountBalanceDOMapper;

    /**
     * 查询单个账号的余额
     *
     * @param queryDo
     * @return
     */
    @Override
    public AccountBalanceDO singleQuery(AccountBalanceDO queryDo) {
        return accountBalanceDOMapper.selectByPrimaryKey(queryDo.getAccountId());
    }
}
