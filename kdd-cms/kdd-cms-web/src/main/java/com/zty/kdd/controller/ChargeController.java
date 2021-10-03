package com.zty.kdd.controller;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.Page;
import com.zty.common.DO.ProductInfoDO;
import com.zty.common.service.AccountService;
import com.zty.framework.annotation.CheckToken;
import com.zty.framework.dto.ResultDTO;
import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.NetworkException;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.DO.ChargeInfoDO;
import com.zty.kdd.DO.ProductChargeRelationDO;
import com.zty.kdd.service.ChargeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收费规则管理
 * 分页、新增、修改(禁用)、绑定、解绑
 */
@RestController
@RequestMapping("/charge")
public class ChargeController {

    private static final Logger log = LoggerFactory.getLogger(ChargeController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private ChargeService chargeService;


    @CheckToken
    @GetMapping("/pageList")
    public ResultDTO pageList(ChargeInfoDO productInfoDO){
        int currentUID = productInfoDO.getCurrentUID();
        // 参数校验
        if (currentUID == 0){
            return ResultDTO.error(403, "用户未登陆");
        }
        // 检查当前账号是否有超管权限
        if (!accountService.checkIsAdmin(currentUID, 1)) {
            return ResultDTO.error(403, "当前账号无权操作");
        }
        // 执行业务
        try {
            Page<ChargeInfoDO> pageResult = chargeService.getByPage(productInfoDO);
            return ResultDTO.success(pageResult.getResult(), pageResult.getTotal());
        } catch (ClassCastException e) {
            log.error("分页查询收费规则列表异常, ", e);
            return ResultDTO.error(500, "类型转换异常"+e.getMessage());
        } catch (ParamCheckException e) {
            log.warn("分页查询收费规则 参数检查异常, ", e);
            if (StringUtils.isNotBlank(e.getKeyName())) {
                return ResultDTO.error(403, e.getErrorMsg(), e);
            } else {
                return ResultDTO.error(403, e.getErrorMsg(), productInfoDO);
            }
        } catch (BusinessException e) {
            log.error("分页查询收费规则 业务处理异常, ", e);
            return ResultDTO.error(500, e.getErrorMsg(), productInfoDO);
        } catch (NetworkException e) {
            log.error("分页查询收费规则 网络通信异常, ", e);
            return ResultDTO.error(504, e.getErrorMsg(), productInfoDO);
        } catch (Exception e) {
            log.error("分页查询收费规则 未知异常, ", e);
            return ResultDTO.error(500, e.getMessage(), productInfoDO);
        }
    }


    @CheckToken
    @GetMapping("/getAll")
    public ResultDTO getAll(ProductChargeRelationDO relationDO){
        int currentUID = relationDO.getCurrentUID();
        // 参数校验
        if (currentUID == 0){
            return ResultDTO.error(403, "用户未登陆");
        }
        if (relationDO.getProductId() == null){
            return ResultDTO.error(403, "产品id必传", relationDO);
        }
        // 执行业务
        try {
            ProductInfoDO productInfoDO = new ProductInfoDO();
            productInfoDO.setId(relationDO.getProductId());
            List<ChargeInfoDO> result = chargeService.getAll(productInfoDO);
            return result == null ? ResultDTO.success(new ArrayList<>()) : ResultDTO.success(result);
        } catch (ClassCastException e) {
            log.error("获取产品所有价格异常, ", e);
            return ResultDTO.error(500, "类型转换异常"+e.getMessage());
        } catch (ParamCheckException e) {
            log.warn("获取产品所有价格 参数检查异常, ", e);
            if (StringUtils.isNotBlank(e.getKeyName())) {
                return ResultDTO.error(403, e.getErrorMsg(), e);
            } else {
                return ResultDTO.error(403, e.getErrorMsg(), relationDO);
            }
        } catch (BusinessException e) {
            log.error("获取产品所有价格 业务处理异常, ", e);
            return ResultDTO.error(500, e.getErrorMsg(), relationDO);
        } catch (NetworkException e) {
            log.error("获取产品所有价格 网络通信异常, ", e);
            return ResultDTO.error(504, e.getErrorMsg(), relationDO);
        } catch (Exception e) {
            log.error("获取产品所有价格 未知异常, ", e);
            return ResultDTO.error(500, e.getMessage(), relationDO);
        }
    }


    @CheckToken
    @PostMapping("/add")
    public ResultDTO add(ChargeInfoDO productInfoDO){
        int currentUID = productInfoDO.getCurrentUID();
        // 参数校验
        if (currentUID == 0){
            return ResultDTO.error(403, "用户未登陆");
        }
        // 检查当前账号是否有超管权限
        if (!accountService.checkIsAdmin(currentUID, 1)) {
            return ResultDTO.error(403, "当前账号无权操作");
        }
        // 执行业务
        try {
            int rows = chargeService.add(productInfoDO);
            return rows == 1 ? ResultDTO.success(rows) : ResultDTO.error(500, "修改条数异常", productInfoDO);
        } catch (ClassCastException e) {
            log.error("新增收费规则异常, ", e);
            return ResultDTO.error(500, "类型转换异常"+e.getMessage());
        } catch (ParamCheckException e) {
            log.warn("新增收费规则 参数检查异常, ", e);
            if (StringUtils.isNotBlank(e.getKeyName())) {
                return ResultDTO.error(403, e.getErrorMsg(), e);
            } else {
                return ResultDTO.error(403, e.getErrorMsg(), productInfoDO);
            }
        } catch (BusinessException e) {
            log.error("新增收费规则 业务处理异常, ", e);
            return ResultDTO.error(500, e.getErrorMsg(), productInfoDO);
        } catch (NetworkException e) {
            log.error("新增收费规则 网络通信异常, ", e);
            return ResultDTO.error(504, e.getErrorMsg(), productInfoDO);
        } catch (Exception e) {
            log.error("新增收费规则 未知异常, ", e);
            return ResultDTO.error(500, e.getMessage(), productInfoDO);
        }
    }

    @CheckToken
    @PostMapping("/update")
    public ResultDTO update(ChargeInfoDO productInfoDO){
        int currentUID = productInfoDO.getCurrentUID();
        // 参数校验
        if (currentUID == 0){
            return ResultDTO.error(403, "用户未登陆");
        }
        // 检查当前账号是否有超管权限
        if (!accountService.checkIsAdmin(currentUID, 1)) {
            return ResultDTO.error(403, "当前账号无权操作");
        }
        // 执行业务
        try {
            int rows = chargeService.update(productInfoDO);
            return rows == 1 ? ResultDTO.success(rows) : ResultDTO.error(500, "修改条数异常", productInfoDO);
        } catch (ClassCastException e) {
            log.error("修改收费规则异常, ", e);
            return ResultDTO.error(500, "类型转换异常"+e.getMessage());
        } catch (ParamCheckException e) {
            log.warn("修改收费规则 参数检查异常, ", e);
            if (StringUtils.isNotBlank(e.getKeyName())) {
                return ResultDTO.error(403, e.getErrorMsg(), e);
            } else {
                return ResultDTO.error(403, e.getErrorMsg(), productInfoDO);
            }
        } catch (BusinessException e) {
            log.error("修改收费规则 业务处理异常, ", e);
            return ResultDTO.error(500, e.getErrorMsg(), productInfoDO);
        } catch (NetworkException e) {
            log.error("修改收费规则 网络通信异常, ", e);
            return ResultDTO.error(504, e.getErrorMsg(), productInfoDO);
        } catch (Exception e) {
            log.error("修改收费规则 未知异常, ", e);
            return ResultDTO.error(500, e.getMessage(), productInfoDO);
        }
    }



    @CheckToken
    @PostMapping("/link")
    public ResultDTO link(ProductChargeRelationDO productRelationDO){
        int currentUID = productRelationDO.getCurrentUID();
        // 参数校验
        if (currentUID == 0){
            return ResultDTO.error(403, "用户未登陆");
        }
        // 检查当前账号是否有超管权限
        if (!accountService.checkIsAdmin(currentUID, 1)) {
            return ResultDTO.error(403, "当前账号无权操作");
        }
        // 执行业务
        try {
            int rows = chargeService.linkProduct(productRelationDO);
            return rows == 1 ? ResultDTO.success(rows) : ResultDTO.error(500, "修改条数异常", productRelationDO);
        } catch (ClassCastException e) {
            log.error("绑定产品收费规则异常, ", e);
            return ResultDTO.error(500, "类型转换异常"+e.getMessage());
        } catch (ParamCheckException e) {
            log.warn("绑定产品收费规则 参数检查异常, ", e);
            if (StringUtils.isNotBlank(e.getKeyName())) {
                return ResultDTO.error(403, e.getErrorMsg(), e);
            } else {
                return ResultDTO.error(403, e.getErrorMsg(), productRelationDO);
            }
        } catch (BusinessException e) {
            log.error("绑定产品收费规则 业务处理异常, ", e);
            return ResultDTO.error(500, e.getErrorMsg(), productRelationDO);
        } catch (NetworkException e) {
            log.error("绑定产品收费规则 网络通信异常, ", e);
            return ResultDTO.error(504, e.getErrorMsg(), productRelationDO);
        } catch (Exception e) {
            log.error("绑定产品收费规则 未知异常, ", e);
            return ResultDTO.error(500, e.getMessage(), productRelationDO);
        }
    }

    @CheckToken
    @PostMapping("/unlink")
    public ResultDTO unlink(ProductChargeRelationDO productRelationDO){
        int currentUID = productRelationDO.getCurrentUID();
        // 参数校验
        if (currentUID == 0){
            return ResultDTO.error(403, "用户未登陆");
        }
        // 检查当前账号是否有超管权限
        if (!accountService.checkIsAdmin(currentUID, 1)) {
            return ResultDTO.error(403, "当前账号无权操作");
        }
        // 执行业务
        try {
            int rows = chargeService.unlinkProduct(productRelationDO);
            return rows == 1 ? ResultDTO.success(rows) : ResultDTO.error(500, "修改条数异常", productRelationDO);
        } catch (ClassCastException e) {
            log.error("解绑产品收费规则异常, ", e);
            return ResultDTO.error(500, "类型转换异常"+e.getMessage());
        } catch (ParamCheckException e) {
            log.warn("解绑产品收费规则 参数检查异常, ", e);
            if (StringUtils.isNotBlank(e.getKeyName())) {
                return ResultDTO.error(403, e.getErrorMsg(), e);
            } else {
                return ResultDTO.error(403, e.getErrorMsg(), productRelationDO);
            }
        } catch (BusinessException e) {
            log.error("解绑产品收费规则 业务处理异常, ", e);
            return ResultDTO.error(500, e.getErrorMsg(), productRelationDO);
        } catch (NetworkException e) {
            log.error("解绑产品收费规则 网络通信异常, ", e);
            return ResultDTO.error(504, e.getErrorMsg(), productRelationDO);
        } catch (Exception e) {
            log.error("解绑产品收费规则 未知异常, ", e);
            return ResultDTO.error(500, e.getMessage(), productRelationDO);
        }
    }
}
