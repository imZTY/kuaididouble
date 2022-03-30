package com.zty.kdd.service.impl;

import com.zty.common.DO.ProductInfoDO;
import com.zty.common.service.ProductService;
import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.ParamCheckException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ProductService productService;

    private final static long INITIAL_BALANCE = 100L;

    /**
     * FIXME: 30/3/2022 管理员充值暂未支持选择套餐，临时使用填充默认套餐的方案
     * @param queryDo
     */
    private void fillProductId(AccountBalanceDO queryDo) {
        // FIXME: 30/3/2022 临时使用默认产品，暂时未支持传入产品
        ProductInfoDO productInfoDO = productService.getDefalutProduct();
        if (productInfoDO == null) {
            log.error("未配置默认产品");
            throw new BusinessException("500", "未配置默认产品");
        }
        if (queryDo.getProductId() == null) {
            queryDo.setProductId(productInfoDO.getId());
        }
    }

    /**
     * 查询单个账号的余额
     *
     * @param queryDo
     * @return
     */
    @Override
    public AccountBalanceDO singleQuery(AccountBalanceDO queryDo) {
        fillProductId(queryDo);
        if (queryDo.getAccountId() == null) {
            throw new ParamCheckException("accountId 不可为空");
        }
        if (queryDo.getProductId() == null) {
            throw new ParamCheckException("productId 不可为空");
        }
        AccountBalanceDO accountBalanceDO = accountBalanceDOMapper.selectByAccountAndProduct(queryDo);
        if (accountBalanceDO == null) {
            // 如果不存在则新增
            log.info("账号:{}, 产品:{}, 暂无笔数余额记录，即将创建", queryDo.getAccountId(), queryDo.getProductId());
            queryDo.setTotalBalance(INITIAL_BALANCE);
            queryDo.setAvailableBalance(INITIAL_BALANCE);
            queryDo.setFrozenBalance(0L);
            accountBalanceDOMapper.insertSelective(queryDo);
            return queryDo;
        }
        return accountBalanceDO;
    }

    @Override
    public int update(AccountBalanceDO accountBalanceDO) {
        if (accountBalanceDO.getAccountId() == null) {
            throw new ParamCheckException("accountId 不可为空");
        }
        if (accountBalanceDO.getProductId() == null) {
            throw new ParamCheckException("productId 不可为空");
        }
        return accountBalanceDOMapper.updateByAccountAndProductSelective(accountBalanceDO);
    }

    /**
     * 检查并冻结账户余额
     *
     * @param queryDo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkAndFrozen(AccountBalanceDO queryDo) throws Exception {
        try {
            fillProductId(queryDo);
            int rows = accountBalanceDOMapper.checkAndFrozen(queryDo);
            if (rows != 1) {
                throw new BusinessException("checkAndFrozen", "账户冻结失败");
            }
            return true;
        } catch (Exception e) {
            log.warn("冻结账户{}的余额失败，", queryDo.getAccountId(), e);
            throw e;
        }
    }

    /**
     * 检查并解冻账户余额
     *
     * @param queryDo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkAndUnfrozen(AccountBalanceDO queryDo) throws Exception {
        try {
            fillProductId(queryDo);
            int rows = accountBalanceDOMapper.checkAndUnfrozen(queryDo);
            if (rows != 1) {
                throw new BusinessException("checkAndUnfrozen", "账户解冻失败");
            }
            return true;
        } catch (Exception e) {
            log.warn("解冻账户{}的余额失败，", queryDo.getAccountId(), e);
            throw e;
        }
    }

    /**
     * 检查并实扣账户余额
     *
     * @param queryDo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkAndCut(AccountBalanceDO queryDo) throws Exception {
        try {
            fillProductId(queryDo);
            int rows = accountBalanceDOMapper.checkAndCut(queryDo);
            if (rows != 1) {
                throw new BusinessException("checkAndCut", "实扣账户余额失败");
            }
            return true;
        } catch (Exception e) {
            log.warn("实扣账户{}的余额失败，", queryDo.getAccountId(), e);
            throw e;
        }
    }
}
