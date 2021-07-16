package com.zty.kdd.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zty.common.service.AccountService;
import com.zty.framework.annotation.CheckToken;
import com.zty.framework.dto.ResultDTO;
import com.zty.kdd.DO.TransQueryLogDO;
import com.zty.kdd.service.TransQueryLogService;

/**
 * @author tianyi
 * @date 2021-06-14 18:14
 */
@RestController
@RequestMapping("/queryLog")
public class QueryLogController {

    private static final Logger log = LoggerFactory.getLogger(QueryLogController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransQueryLogService transQueryLogService;

    @CheckToken
    @GetMapping("/pageList")
    public ResultDTO pageList(TransQueryLogDO queryLogDO) {
        log.info("订单查询，入参:{}", JSONObject.toJSONString(queryLogDO));
        // 权限检查，若非管理员，只能查自己的请求记录
        int currentUID = queryLogDO.getCurrentUID();
        if (currentUID == 0) {
            return ResultDTO.error(403, "用户未登陆");
        } else if (!this.accountService.checkIsAdmin(currentUID, 1,4)) {
            // 非管理员则只能查自己
            queryLogDO.setAccountId(currentUID);
        } else {
            // 是管理员，暂无其他处理
        }
        // 分页查询并返回
        Page<TransQueryLogDO> pageResult = this.transQueryLogService.pageListByPage(queryLogDO);
        return ResultDTO.success(pageResult.getResult(), pageResult.getTotal());
    }
}
