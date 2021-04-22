package com.zty.kdd.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zty.framework.constant.Disabled;
import com.zty.kdd.DO.SecretKeyInfoDO;
import com.zty.kdd.DO.example.SecretKeyInfoDOExample;
import com.zty.kdd.constant.SecretType;
import com.zty.kdd.dao.SecretKeyInfoDOMapper;
import com.zty.kdd.service.SecretKeyService;

/**
 * @author tianyi
 * @date 2021-04-13 20:46
 */
@Service
public class SecretKeyServiceImpl implements SecretKeyService {

    private static final Logger log = LoggerFactory.getLogger(SecretKeyServiceImpl.class);

    @Autowired
    private SecretKeyInfoDOMapper secretKeyInfoDOMapper;

    /**
     * 根据账号ID获取秘钥信息
     *
     * @param secretKeyInfoReq
     * @return
     */
    @Override
    public SecretKeyInfoDO getSecretByAccountId(SecretKeyInfoDO secretKeyInfoReq) {
        if (secretKeyInfoReq.getAccountId() == null || secretKeyInfoReq.getAccountId() == 0) {
            throw new IllegalArgumentException("入参缺少accountId");
        }
        SecretKeyInfoDOExample example = new SecretKeyInfoDOExample();
        example.createCriteria()
                .andAccountIdEqualTo(secretKeyInfoReq.getAccountId());
        List<SecretKeyInfoDO> selectResult = secretKeyInfoDOMapper.selectByExample(example);
        if (selectResult.size() == 0) {
            // 不存在则新增
            SecretKeyInfoDO newSecretKeyDO = getBlankNewSecretKeyDO();
            newSecretKeyDO.setAccountId(secretKeyInfoReq.getAccountId());
            newSecretKeyDO.setAccountId(secretKeyInfoReq.getAccountId());
            int rows = secretKeyInfoDOMapper.insertSelective(newSecretKeyDO);
            if (rows == 0) {
                throw new RuntimeException("新增秘钥失败");
            }
            return newSecretKeyDO;
        } else {
            // 存在则判断是否可用（要查了之后再判断）
            if (Disabled.FALSE == selectResult.get(0).getDisabled()) {
                return selectResult.get(0);
            } else {
                throw new IllegalArgumentException("当前账号不可用");
            }
        }
    }

    /**
     * 获取全新的秘钥对象
     * @return
     */
    private SecretKeyInfoDO getBlankNewSecretKeyDO() {
        SecretKeyInfoDO secretKeyInfoDO = new SecretKeyInfoDO();
        secretKeyInfoDO.setCustomerCode(RandomStringUtils.random(10, false, true));
        secretKeyInfoDO.setSecretKey(RandomStringUtils.random(20, true, true));
        secretKeyInfoDO.setSecretType(SecretType.MAP_TRACK_QUERY);
        secretKeyInfoDO.setDisabled(Disabled.FALSE);
        secretKeyInfoDO.setCreateTime(new Date());
        return secretKeyInfoDO;
    }

    /**
     * 根据合作伙伴编码获取秘钥信息
     *
     * @param secretKeyInfoDO
     * @return
     */
    @Override
    public SecretKeyInfoDO getSecretByCustomerCode(SecretKeyInfoDO secretKeyInfoDO) {
        return null;
    }
}
