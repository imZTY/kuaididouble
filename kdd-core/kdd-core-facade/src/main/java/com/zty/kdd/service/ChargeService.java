package com.zty.kdd.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.zty.common.DO.ProductInfoDO;
import com.zty.kdd.DO.ChargeInfoDO;
import com.zty.kdd.DO.ProductChargeRelationDO;

/**
 * 收费规则
 */
public interface ChargeService {

    /**
     * 分页获取收费规则
     * @param chargeInfoDO
     * @return
     */
    public Page<ChargeInfoDO> getByPage(ChargeInfoDO chargeInfoDO);

    /**
     * 获取满足条件的所有收费规则
     * @param chargeInfoDO
     * @return
     */
    public List<ChargeInfoDO> getAll(ChargeInfoDO chargeInfoDO);

    /**
     * 获取该产品的所有收费规则
     * @param productInfoDO
     * @return
     */
    public List<ChargeInfoDO> getAll(ProductInfoDO productInfoDO);

    /**
     * 新增收费规则
     * @return 成功条数
     */
    public int add(ChargeInfoDO chargeInfoDO);

    /**
     * 修改收费规则
     * @return 成功条数
     */
    public int update(ChargeInfoDO chargeInfoDO);

    /**
     * 绑定产品
     * @return 成功条数
     */
    public int linkProduct(ProductChargeRelationDO relationDO);

    /**
     * 解除产品
     * @return 成功条数
     */
    public int unlinkProduct(ProductChargeRelationDO relationDO);

    public ChargeInfoDO findById(Integer id);
}
