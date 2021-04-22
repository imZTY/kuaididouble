package com.zty.kdd.service;

import com.zty.kdd.DO.AccountBalanceDO;

/**
 * @author tianyi
 * @date 2021-04-18 12:13
 */
public interface BalanceService {

    /**
     * 查询单个账号的余额
     * @param queryDo
     * @return
     */
    public AccountBalanceDO singleQuery(AccountBalanceDO queryDo);
}
