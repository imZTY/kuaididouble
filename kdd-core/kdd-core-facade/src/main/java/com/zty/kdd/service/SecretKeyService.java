package com.zty.kdd.service;

import com.zty.kdd.DO.SecretKeyInfoDO;

/**
 * @author tianyi
 * @date 2021-04-13 20:23
 */
public interface SecretKeyService {

    /**
     * 根据账号ID获取秘钥信息
     * @param secretKeyInfoDO
     * @return
     */
    public SecretKeyInfoDO getSecretByAccountId(SecretKeyInfoDO secretKeyInfoDO);


    /**
     * 根据合作伙伴编码获取秘钥信息
     * @param secretKeyInfoDO
     * @return
     */
    public SecretKeyInfoDO getSecretByCustomerCode(SecretKeyInfoDO secretKeyInfoDO);

}
