package com.zty.kdd.controller;

import java.io.IOException;
import java.util.Date;

import cn.hutool.core.math.Money;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.zty.kdd.DO.ChargeInfoDO;
import com.zty.kdd.config.PayCenterConfig;
import com.zty.kdd.service.ChargeService;
import com.zty.pay.DO.OrderInfoDO;
import com.zty.pay.constant.OrderMethod;
import com.zty.pay.constant.OrderStatus;
import com.zty.pay.constant.OrderType;
import com.zty.pay.service.PayOrderService;
import com.zty.pay.utils.MoneyUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zty.common.service.AccountService;
import com.zty.framework.annotation.CheckToken;
import com.zty.framework.dto.ResultDTO;
import com.zty.kdd.service.KddOrderService;

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
    private KddOrderService kddOrderService;

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private PayCenterConfig payCenterConfig;

    /**
     * 网站支付
     * @return
     */
    @CheckToken
    @PostMapping("/pcPay")
    public ResultDTO pcPay(OrderInfoDO pcPayRequest, @RequestParam(name = "returnUrl",defaultValue = "returnUrl")String returnUrl, HttpServletResponse response) {
        log.info("网站充值，入参(注意!这里的id是收费规则id):{}", JSONObject.toJSONString(pcPayRequest));
        int currentUID = pcPayRequest.getCurrentUID();
        if (currentUID == 0) {
            return ResultDTO.error(403, "用户未登陆");
        }
        int chargeId = Math.toIntExact(pcPayRequest.getId());
        ChargeInfoDO chargeInfoDO = chargeService.findById(chargeId);
        if (chargeInfoDO == null) {
            log.warn("充值套餐{}不存在", chargeId);
            return ResultDTO.error(403, "收费套餐"+chargeId+"不存在");
        }
        if (pcPayRequest.getOrderMethod() == null) {
            log.warn("未传入充值方式{}", pcPayRequest.getOrderMethod());
            return ResultDTO.error(403, "未传入充值方式");
        }
        if (OrderMethod.ALI_PAY != pcPayRequest.getOrderMethod()) {
            log.warn("暂不支持当前充值方式{}", pcPayRequest.getOrderMethod());
            return ResultDTO.error(403, "仅支持支付宝网站支付");
        }
        // 根据收费规则，创建订单基本信息
        OrderInfoDO order = parseOrderInfoFromCharge(chargeInfoDO, currentUID, pcPayRequest);
        long orderId = payOrderService.createNewOrder(order);
        log.info("已生成本地订单号:{}", orderId);
        // 跳转到对应的支付中心
        String payCenterUrl = payCenterConfig.getAlipayWebpayUrl(MoneyUtil.fenToYuan(order.getActualAmount()),
                String.valueOf(orderId),
                "kdd网站支付",
                "余额套餐充值",
                returnUrl);
        try {
            response.sendRedirect(payCenterUrl);
            return ResultDTO.success();
        } catch (Exception e) {
            log.error("跳转至支付中心{}异常: ", payCenterUrl, e);
            return ResultDTO.error(500, "跳转至支付中心异常:"+e.getMessage());
        }
    }

    private OrderInfoDO parseOrderInfoFromCharge(ChargeInfoDO chargeInfoDO, int accountId, OrderInfoDO request) {
        OrderInfoDO orderInfo = new OrderInfoDO();
        orderInfo.setAccountId(accountId);
        orderInfo.setStatus(OrderStatus.INIT);
        orderInfo.setOrderMethod(request.getOrderMethod());
        orderInfo.setOrderType(OrderType.CHARGE);
        orderInfo.setThirdOrderNo("");
        orderInfo.setOrderAmount(chargeInfoDO.getChargePrice());
        orderInfo.setActualAmount(chargeInfoDO.getChargePrice());
        orderInfo.setBalanceChange(chargeInfoDO.getAmount());
        orderInfo.setCreateBy(accountId);
        orderInfo.setCreateTime(new Date());
        return orderInfo;
    }

    /**
     * 管理员充值
     * @param orderInfoDO
     * @return
     * @throws Exception
     */
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
                OrderInfoDO existedOrder = payOrderService.checkAndGet(orderInfoDO);
                if (existedOrder != null && existedOrder.getOrderType() == OrderType.CHARGE) {
                    return ResultDTO.error(403, "充值订单已存在", orderInfoDO);
                }
            } else if (OrderType.REFUND == orderInfoDO.getOrderType()) {
                // 退款
                if (orderInfoDO.getId() == null) {
                    return ResultDTO.error(403, "未传入订单ID");
                }
                // 检查退款订单是否已存在
                OrderInfoDO existedOrder = payOrderService.checkAndGet(orderInfoDO);
                if (existedOrder != null && existedOrder.getOrderType() == OrderType.REFUND) {
                    return ResultDTO.error(403, "退款订单已存在", orderInfoDO);
                }
            } else {
                // 无法识别
                return ResultDTO.error(403, "无法识别订单类型");
            }
            // 调用事务充值方法（先创建订单，再改余额）
            orderInfoDO.setCreateBy(currentUID);
            orderInfoDO.setActualAmount(orderInfoDO.getOrderAmount());
            boolean isOk = this.kddOrderService.chargeWithTransaction(orderInfoDO);
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
        Page<OrderInfoDO> pageResult = this.payOrderService.pageOrderListByPage(orderInfoDO);
        return ResultDTO.success(pageResult.getResult(), pageResult.getTotal());
    }
}
