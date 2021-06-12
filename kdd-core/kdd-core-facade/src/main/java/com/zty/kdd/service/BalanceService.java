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

    /**
     * 检查并冻结账户余额
     * @param queryDo
     * @return
     */
    public boolean checkAndFrozen(AccountBalanceDO queryDo) throws Exception;

    /**
     * 检查并解冻账户余额
     * @param queryDo
     * @return
     */
    public boolean checkAndUnfrozen(AccountBalanceDO queryDo) throws Exception;

    /**
     * 检查并实扣账户余额
     * @param queryDo
     * @return
     */
    public boolean checkAndCut(AccountBalanceDO queryDo) throws Exception;

    int update(AccountBalanceDO accountBalanceDO);
}
