package com.zty.kdd.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zty.bo.service.FileService;
import com.zty.common.DO.AccountInfoDO;
import com.zty.common.DO.FileInfoDO;
import com.zty.common.DO.UserInfoDO;
import com.zty.common.enums.FileKindEnum;
import com.zty.common.service.AccountService;
import com.zty.common.service.UserService;
import com.zty.framework.annotation.CheckToken;
import com.zty.framework.dto.ResultDTO;
import com.zty.kdd.dto.CertificateDetailDTO;

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
        if (currentUID != userInfoDO.getAccountId()) {
            // 检查当前账号是否有超管权限
            if (!accountService.checkIsAdmin(currentUID)) {
                return ResultDTO.error(403, "当前账号无权操作");
            }
        }
        AccountInfoDO dbAccountDO = accountService.findById(userInfoDO.getAccountId());
        if (dbAccountDO == null) {
            return ResultDTO.error(403, "账号不存在");
        } else if (dbAccountDO.getDisabled() == 1){
            return ResultDTO.error(403, "账号状态不可用");
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
        try {
            return ResultDTO.success(new CertificateDetailDTO(dbAccountDO, dbUserDO, myFiles));
        } catch (IllegalAccessException e) {
            log.error("反射脱敏时，字段访问非法", e);
            return ResultDTO.error(500, "反射脱敏时，字段访问非法");
        } catch (InstantiationException e) {
            log.error("反射脱敏时，实例化失败", e);
            return ResultDTO.error(500, "反射脱敏时，实例化失败");
        }
    }

    // TODO 分页查询

    // 修改详情 - 复用UserController.updateMine

}
