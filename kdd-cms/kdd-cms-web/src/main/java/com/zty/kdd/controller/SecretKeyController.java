package com.zty.kdd.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zty.common.service.AccountService;
import com.zty.framework.annotation.CheckToken;
import com.zty.framework.dto.ResultDTO;
import com.zty.kdd.DO.SecretKeyInfoDO;
import com.zty.kdd.service.SecretKeyService;

/**
 * 秘钥管理
 * @author tianyi
 * @date 2021-04-07 00:51
 */
@RestController
@RequestMapping("/secret")
public class SecretKeyController {

    private static final Logger log = LoggerFactory.getLogger(SecretKeyController.class);

    @Autowired
    private SecretKeyService secretKeyService;

    @Autowired
    private AccountService accountService;

    @CheckToken
    @PostMapping("/getMine")
    public ResultDTO getMine(SecretKeyInfoDO secretKeyInfoDO) {
        int currentUID = secretKeyInfoDO.getCurrentUID();
        // 参数校验
        if (currentUID == 0){
            return ResultDTO.error(403, "用户未登陆");
        }
        // 检查当前账号是否有超管权限
        if (accountService.checkIsAdmin(currentUID, 2)) {
            // 未认证的用户，无法查看
            return ResultDTO.error(403, "当前账号无权操作");
        }
        // 强制只能查看自己的信息
        secretKeyInfoDO.setAccountId(currentUID);
        return ResultDTO.success(secretKeyService.getSecretByAccountId(secretKeyInfoDO));
    }
}
