package com.zty.kdd.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zty.common.DO.AccountProductRelationDO;
import com.zty.common.DO.ProductInfoDO;
import com.zty.common.DO.example.AccountProductRelationDOExample;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.DO.ChargeInfoDO;
import com.zty.kdd.DO.ProductChargeRelationDO;
import com.zty.kdd.DO.example.ChargeInfoDOExample;
import com.zty.kdd.DO.example.ProductChargeRelationDOExample;
import com.zty.kdd.dao.ChargeInfoDOMapper;
import com.zty.kdd.dao.ProductChargeRelationDOMapper;
import com.zty.kdd.service.ChargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author: tianyi.zeng
 * @create: 27/9/2021 - 下午 3:27
 */
@Service
public class ChargeServiceImpl implements ChargeService {

    private static final Logger log = LoggerFactory.getLogger(ChargeServiceImpl.class);

    @Autowired
    private ChargeInfoDOMapper chargeInfoDOMapper;

    @Autowired
    private ProductChargeRelationDOMapper chargeRelationDOMapper;
    /**
     * 分页获取收费规则
     *
     * @param chargeInfoDO
     * @return
     */
    @Override
    public Page<ChargeInfoDO> getByPage(ChargeInfoDO chargeInfoDO) {
        ChargeInfoDOExample example = new ChargeInfoDOExample();
        if (chargeInfoDO.getChargeType() != null) {
            example.createCriteria().andChargeTypeEqualTo(chargeInfoDO.getChargeType());
        }
        if (chargeInfoDO.getCreateBy() != null) {
            example.createCriteria().andCreateByEqualTo(chargeInfoDO.getCreateBy());
        }
        example.setOrderByClause("charge_price ASC");
        PageHelper.startPage(chargeInfoDO.getPage(), chargeInfoDO.getRows());
        return (Page<ChargeInfoDO>) chargeInfoDOMapper.selectByExample(example);
    }

    /**
     * 获取满足条件的所有收费规则
     *
     * @param chargeInfoDO
     * @return
     */
    @Override
    public List<ChargeInfoDO> getAll(ChargeInfoDO chargeInfoDO) {
        ChargeInfoDOExample example = new ChargeInfoDOExample();
        if (chargeInfoDO.getChargeType() != null) {
            example.createCriteria().andChargeTypeEqualTo(chargeInfoDO.getChargeType());
        }
        if (chargeInfoDO.getCreateBy() != null) {
            example.createCriteria().andCreateByEqualTo(chargeInfoDO.getCreateBy());
        }
        example.setOrderByClause("charge_price ASC");
        return chargeInfoDOMapper.selectByExample(example);
    }

    /**
     * 获取该产品的所有收费规则
     *
     * @param productInfoDO
     * @return
     */
    @Override
    public List<ChargeInfoDO> getAll(ProductInfoDO productInfoDO) {
        if (productInfoDO.getId() == null) {
            throw new ParamCheckException("产品id不可为空");
        }
        ProductChargeRelationDOExample relationDOExample = new ProductChargeRelationDOExample();
        relationDOExample.createCriteria()
                .andProductIdEqualTo(productInfoDO.getId())
                .andDisabledEqualTo((byte) 0);
        List<ProductChargeRelationDO> existRelations = chargeRelationDOMapper.selectByExample(relationDOExample);
        if (CollectionUtils.isEmpty(existRelations)) {
            return null;
        }
        ChargeInfoDOExample example = new ChargeInfoDOExample();
        example.createCriteria().andIdIn(existRelations.stream().map(ProductChargeRelationDO::getChargeId).collect(Collectors.toList()));
        example.setOrderByClause("charge_price ASC");
        return chargeInfoDOMapper.selectByExample(example);
    }

    /**
     * 新增收费规则
     *
     * @param chargeInfoDO
     * @return 成功条数
     */
    @Override
    public int add(ChargeInfoDO chargeInfoDO) {
        if (chargeInfoDO.getDescription() == null) {
            log.warn("收费规则说明不可为空");
            throw new ParamCheckException("收费规则说明不可为空");
        }
        if (chargeInfoDO.getChargePrice() == null) {
            log.warn("收费价格不可为空");
            throw new ParamCheckException("收费价格不可为空");
        }
        if (chargeInfoDO.getAmount() == null) {
            log.warn("充值数量不可为空");
            throw new ParamCheckException("充值数量不可为空");
        }
        chargeInfoDO.setCreateTime(new Date());
        chargeInfoDO.setUpdateTime(new Date());
        chargeInfoDO.setCreateBy(chargeInfoDO.getCurrentUID());
        chargeInfoDO.setDisabled((byte) 0);
        return chargeInfoDOMapper.insertSelective(chargeInfoDO);
    }

    /**
     * 修改收费规则
     *
     * @param chargeInfoDO
     * @return 成功条数
     */
    @Override
    public int update(ChargeInfoDO chargeInfoDO) {
        if (chargeInfoDO.getId() == null) {
            throw new ParamCheckException("收费规则id不可为空");
        }
        chargeInfoDO.setUpdateTime(new Date());
        return chargeInfoDOMapper.updateByPrimaryKeySelective(chargeInfoDO);
    }

    /**
     * 绑定产品
     *
     * @param relationDO
     * @return 成功条数
     */
    @Override
    public int linkProduct(ProductChargeRelationDO relationDO) {
        if (relationDO.getProductId() == null) {
            throw new ParamCheckException("产品id不可为空");
        }
        if (relationDO.getChargeId() == null) {
            throw new ParamCheckException("收费规则id不可为空");
        }
        ProductChargeRelationDOExample relationDOExample = new ProductChargeRelationDOExample();
        relationDOExample.createCriteria()
                .andChargeIdEqualTo(relationDO.getChargeId())
                .andProductIdEqualTo(relationDO.getProductId());
        List<ProductChargeRelationDO> existedRelations = chargeRelationDOMapper.selectByExample(relationDOExample);
        if (!CollectionUtils.isEmpty(existedRelations)) {
            ProductChargeRelationDO existedRelation = existedRelations.get(0);
            if (existedRelation.getDisabled() == 0) {
                return 1;
            } else {
                existedRelation.setDisabled((byte) 0);
                existedRelation.setUpdateTime(new Date());
                return chargeRelationDOMapper.updateByPrimaryKeySelective(existedRelation);
            }
        } else {
            relationDO.setCreateTime(new Date());
            relationDO.setUpdateTime(new Date());
            relationDO.setCreateBy(relationDO.getCurrentUID());
            relationDO.setDisabled((byte) 0);
            return chargeRelationDOMapper.insertSelective(relationDO);
        }
    }

    /**
     * 解除产品
     *
     * @param relationDO
     * @return 成功条数
     */
    @Override
    public int unlinkProduct(ProductChargeRelationDO relationDO) {
        if (relationDO.getProductId() == null) {
            throw new ParamCheckException("产品id不可为空");
        }
        if (relationDO.getChargeId() == null) {
            throw new ParamCheckException("收费规则id不可为空");
        }
        ProductChargeRelationDOExample relationDOExample = new ProductChargeRelationDOExample();
        relationDOExample.createCriteria()
                .andChargeIdEqualTo(relationDO.getChargeId())
                .andProductIdEqualTo(relationDO.getProductId());
        List<ProductChargeRelationDO> existedRelations = chargeRelationDOMapper.selectByExample(relationDOExample);
        if (CollectionUtils.isEmpty(existedRelations)) {
            log.warn("查不到收费规则{}与产品{}的绑定关系", relationDO.getChargeId(), relationDO.getProductId());
            return 1;
        } else {
            ProductChargeRelationDO existedRelation = existedRelations.get(0);
            if (existedRelation.getDisabled() == 1) {
                return 1;
            }
            existedRelation.setDisabled((byte) 1);
            existedRelation.setUpdateTime(new Date());
            return chargeRelationDOMapper.updateByPrimaryKeySelective(existedRelation);
        }
    }

    @Override
    public ChargeInfoDO findById(Integer id) {
        return chargeInfoDOMapper.selectByPrimaryKey(id);
    }
}
