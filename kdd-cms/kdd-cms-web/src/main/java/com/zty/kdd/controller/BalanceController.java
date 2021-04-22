package com.zty.kdd.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zty.common.service.AccountService;
import com.zty.framework.annotation.CheckToken;
import com.zty.framework.dto.ResultDTO;
import com.zty.kdd.DO.AccountBalanceDO;
import com.zty.kdd.service.BalanceService;

/**
 * @author tianyi
 * @date 2021-04-07 00:53
 */
@RestController
@RequestMapping("/balance")
public class BalanceController {

    private static final Logger log = LoggerFactory.getLogger(BalanceController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private BalanceService balanceService;

    @CheckToken
    @GetMapping("/singleQuery")
    public ResultDTO singleQuery(AccountBalanceDO queryDo) {
        log.info("余额查询，入参:{}", JSONObject.toJSONString(queryDo));
        // 权限检查，若非管理员，只能查自己的订单
        int currentUID = queryDo.getCurrentUID();
        if (currentUID == 0) {
            return ResultDTO.error(403, "用户未登陆");
        } else if (!this.accountService.checkIsAdmin(currentUID, 1)) {
            // 若非管理员，只能查自己的
            queryDo.setAccountId(currentUID);
        }
        // 分页查询并返回
        AccountBalanceDO result = this.balanceService.singleQuery(queryDo);
        return ResultDTO.success(result);
    }
}
