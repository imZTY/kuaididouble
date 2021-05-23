package com.zty.kdd.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zty.bo.service.FileService;
import com.zty.common.DO.AccountInfoDO;
import com.zty.common.DO.AccountRoleRelationDO;
import com.zty.common.DO.FileInfoDO;
import com.zty.common.DO.UserInfoDO;
import com.zty.common.dto.LoginInfoDTO;
import com.zty.common.enums.FileKindEnum;
import com.zty.common.service.AccountService;
import com.zty.common.service.RoleService;
import com.zty.common.service.UserService;
import com.zty.framework.annotation.CheckToken;
import com.zty.framework.dto.ResultDTO;
import com.zty.framework.util.ReflectUtil;
import com.zty.kdd.ao.DealCheckRequest;
import com.zty.kdd.request.CertificateDetailDTO;

/**
 * 进件报备
 * @author tianyi
 * @date 2021-04-07 00:47
 */
@RestController
@RequestMapping("/certificate")
public class CertificateController {

    private static final Logger log = LoggerFactory.getLogger(CertificateController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FileService fileService;

    // 获取资料详情
    @CheckToken
    @GetMapping("/getDetail")
    public ResultDTO getDetail(UserInfoDO userInfoDO) {
        int currentUID = userInfoDO.getCurrentUID();
        // 参数校验
        if (currentUID == 0){
            return ResultDTO.error(403, "用户未登陆");
        }
        // 强制只能查看自己的信息
        if (userInfoDO.getAccountId() != 0 && currentUID != userInfoDO.getAccountId()) {
            // 检查当前账号是否有超管权限
            if (!accountService.checkIsAdmin(currentUID, 1,4)) {
                return ResultDTO.error(403, "当前账号无权操作");
            }
        } else {
            userInfoDO.setAccountId(currentUID);
        }
        AccountInfoDO dbAccountDO = accountService.findById(userInfoDO.getAccountId());
        if (dbAccountDO == null) {
            return ResultDTO.error(403, "账号不存在");
        } else if (dbAccountDO.getDisabled() == 1){
            return ResultDTO.error(403, "账号状态不可用");
        }
        LoginInfoDTO accountInfo;
        try {
            accountInfo = (LoginInfoDTO) ReflectUtil.propertyMapper(dbAccountDO,
                            AccountInfoDO.class,
                            LoginInfoDTO.class);
        } catch (IllegalAccessException e) {
            log.error("反射脱敏时，字段访问非法", e);
            return ResultDTO.error(500, "反射脱敏时，字段访问非法");
        } catch (InstantiationException e) {
            log.error("反射脱敏时，实例化失败", e);
            return ResultDTO.error(500, "反射脱敏时，实例化失败");
        }
        List<AccountRoleRelationDO> relationList = roleService.getRoleIdByAccount(dbAccountDO.getId());
        if (relationList.size() == 0) {
            accountInfo.setRoleId("2");
        } else {
            accountInfo.setRoleId(relationList.get(0).getRoleId() + "");
        }
        UserInfoDO dbUserDO = userService.findUserInfo(userInfoDO.getAccountId());
        List<FileInfoDO> myFiles = fileService.listMine(userInfoDO.getAccountId(),
                Arrays.asList(
                        FileKindEnum.ID_CARD_FRONT.getValue(),
                        FileKindEnum.ID_CARD_BACK.getValue(),
                        FileKindEnum.BUSINESS_LICENSE.getValue(),
                        FileKindEnum.MAN_WITH_LICENSE.getValue()
                )
        );
        return ResultDTO.success(new CertificateDetailDTO(
                accountInfo,
                dbUserDO,
                myFiles));
    }


    // 未审批列表
    @CheckToken
    @GetMapping("/pageListUnfinish")
    public ResultDTO pageListUnfinish(UserInfoDO userInfoDO) throws IllegalAccessException, InstantiationException {
        int currentUID = userInfoDO.getCurrentUID();
        // 参数校验
        if (currentUID == 0){
            return ResultDTO.error(403, "用户未登陆");
        }
        // 检查当前账号是否有超管权限
        if (!accountService.checkIsAdmin(currentUID, 1,4)) {
            return ResultDTO.error(403, "当前账号无权操作");
        }
        AccountInfoDO pageParam = new AccountInfoDO();
        pageParam.setPage(userInfoDO.getPage());
        pageParam.setRows(userInfoDO.getRows());
        // 这里的 2 对应于"未审核用户"
        Page<AccountInfoDO> pageResult = accountService.getAccountPageByRoleId(pageParam, Collections.singletonList(2));
        if (CollectionUtils.isEmpty(pageResult.getResult())) {
            return ResultDTO.success(null, pageResult.getTotal());
        }
        List<LoginInfoDTO> loginInfoList = accountService.parseAccountListToLoginInfoList(pageResult.getResult());

        try {
            List<CertificateDetailDTO> resultData = loginInfoList.stream().map(loginInfoDTO -> {
                UserInfoDO dbUserDO = userService.findUserInfo(loginInfoDTO.getId());
                List<FileInfoDO> myFiles = fileService.listMine(dbUserDO.getAccountId(),
                        Arrays.asList(
                                FileKindEnum.ID_CARD_FRONT.getValue(),
                                FileKindEnum.ID_CARD_BACK.getValue(),
                                FileKindEnum.BUSINESS_LICENSE.getValue(),
                                FileKindEnum.MAN_WITH_LICENSE.getValue()
                        )
                );
                return new CertificateDetailDTO(
                        loginInfoDTO,
                        dbUserDO,
                        myFiles);
            }).collect(Collectors.toList());
            return ResultDTO.success(resultData, pageResult.getTotal());
        } catch (ClassCastException e) {
            log.error("反射脱敏异常", e);
            return ResultDTO.error(500, e.getMessage());
        }
    }
    // 修改详情 - 复用UserController.updateMine


    @CheckToken
    @PostMapping({"/dealCheck"})
    public ResultDTO dealCheck(DealCheckRequest dealCheckRequest) {
        log.info("审核通过，入参:{}", JSONObject.toJSONString(dealCheckRequest));
        int currentUID = dealCheckRequest.getCurrentUID();
        if (currentUID == 0) {
            return ResultDTO.error(403, "用户未登陆");
        } else if (!this.accountService.checkIsAdmin(currentUID, 1,4)) {
            return ResultDTO.error(403, "当前账号无权操作");
        } else {
            boolean isOk = true;
            if (dealCheckRequest.getIsPass()) {
                // 审核通过，修改角色为已认证
                AccountRoleRelationDO relationDO = new AccountRoleRelationDO();
                relationDO.setAccountId(dealCheckRequest.getAccountId());
                relationDO.setRoleId(3);
                this.roleService.setRole(relationDO);
            } else {
                // 不通过
            }
            UserInfoDO userInfoDO = new UserInfoDO();
            userInfoDO.setAccountId(dealCheckRequest.getAccountId());
            userInfoDO.setDescription(dealCheckRequest.getDescription());
            userInfoDO.setUpdateTime(new Date());
            this.userService.updateUserInfo(userInfoDO);
            return isOk ? ResultDTO.success() : ResultDTO.error(500, "修改失败");
        }
    }
}
