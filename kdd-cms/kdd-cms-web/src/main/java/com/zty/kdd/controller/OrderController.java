package com.zty.kdd.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zty.common.service.AccountService;
import com.zty.framework.annotation.CheckToken;
import com.zty.framework.dto.ResultDTO;
import com.zty.kdd.DO.OrderInfoDO;
import com.zty.kdd.constant.OrderMethod;
import com.zty.kdd.constant.OrderType;
import com.zty.kdd.service.OrderService;

/**
 * 充值/退款订单
 * @author tianyi
 * @date 2021-04-07 00:52
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @CheckToken
    @PostMapping("/charge")
    public ResultDTO charge(OrderInfoDO orderInfoDO) throws Exception {
        log.info("账号充值，入参:{}", JSONObject.toJSONString(orderInfoDO));
        // 权限检查，一期仅开放管理员充值
        int currentUID = orderInfoDO.getCurrentUID();
        if (currentUID == 0) {
            return ResultDTO.error(403, "用户未登陆");
        } else if (!this.accountService.checkIsAdmin(currentUID, 1)) {
            return ResultDTO.error(403, "当前账号无权操作");
        } else {
            // 参数检查
            if (orderInfoDO.getOrderMethod() == null) {
                return ResultDTO.error(403, "未传入充值方式");
            }
            if (!OrderMethod.contains(orderInfoDO.getOrderMethod())) {
                return ResultDTO.error(403, "不支持该充值方式");
            }
            if (orderInfoDO.getOrderType() == null) {
                return ResultDTO.error(403, "未传入订单类型");
            }
            if (orderInfoDO.getAccountId() == null) {
                return ResultDTO.error(403, "未传入充值账户ID");
            }
            // 根据订单类型（充值/退款）分别进行处理
            if (OrderType.CHARGE == orderInfoDO.getOrderType()) {
                // 充值
                if (orderInfoDO.getThirdOrderNo() == null) {
                    return ResultDTO.error(403, "未传入第三方订单号");
                }
                if (orderInfoDO.getOrderAmount() == null) {
                    return ResultDTO.error(403, "未传入订单总金额");
                }
                if (orderInfoDO.getActualAmount() == null) {
                    return ResultDTO.error(403, "未传入实付金额");
                }
                if (orderInfoDO.getBalanceChange() == null) {
                    return ResultDTO.error(403, "未传入充值条数");
                }
                // 检查充值订单是否已存在
                OrderInfoDO existedOrder = orderService.checkAndGetCharge(orderInfoDO);
                if (existedOrder != null) {
                    return ResultDTO.error(403, "充值订单已存在", orderInfoDO);
                }
            } else if (OrderType.REFUND == orderInfoDO.getOrderType()) {
                // 退款
                if (orderInfoDO.getId() == null) {
                    return ResultDTO.error(403, "未传入订单ID");
                }
                // 检查退款订单是否已存在
                OrderInfoDO existedOrder = orderService.checkAndGetRefund(orderInfoDO);
                if (existedOrder != null) {
                    return ResultDTO.error(403, "退款订单已存在", orderInfoDO);
                }
            } else {
                // 无法识别
                return ResultDTO.error(403, "无法识别订单类型");
            }
            // 调用事务充值方法（先创建订单，再改余额）
            orderInfoDO.setCreateBy(currentUID);
            orderInfoDO.setActualAmount(orderInfoDO.getOrderAmount());
            boolean isOk = this.orderService.chargeWithTransaction(orderInfoDO);
            return isOk ? ResultDTO.success() : ResultDTO.error(500, "充值失败");
        }
    }

    @CheckToken
    @GetMapping("/pageList")
    public ResultDTO pageList(OrderInfoDO orderInfoDO) {
        log.info("订单查询，入参:{}", JSONObject.toJSONString(orderInfoDO));
        // 权限检查，若非管理员，只能查自己的订单
        int currentUID = orderInfoDO.getCurrentUID();
        if (currentUID == 0) {
            return ResultDTO.error(403, "用户未登陆");
        } else if (!this.accountService.checkIsAdmin(currentUID, 1)) {
            // 非管理员则只能查自己
            orderInfoDO.setAccountId(currentUID);
        }
        // 分页查询并返回
        Page<OrderInfoDO> pageResult = this.orderService.pageOrderListByPage(orderInfoDO);
        return ResultDTO.success(pageResult.getResult(), pageResult.getTotal());
    }
}
