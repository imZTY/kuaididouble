package com.zty.kdd.request;

import java.util.List;
import java.util.stream.Collectors;

import com.zty.common.DO.FileInfoDO;
import com.zty.common.DO.UserInfoDO;
import com.zty.common.dto.LoginInfoDTO;

/**
 * 用户进件详情
 * @author tianyi
 * @date 2021-04-08 02:21
 */
public class CertificateDetailDTO {

    private LoginInfoDTO accountInfo;

    private UserInfoDO userInfo;

    private List<FileInfoDO.FileMsg> certificateFiles;

    public CertificateDetailDTO(LoginInfoDTO accountInfoDO, UserInfoDO userInfoDO, List<FileInfoDO> certificateFiles) {
        this.accountInfo = accountInfoDO;
        this.userInfo = userInfoDO;
        this.certificateFiles = certificateFiles.stream()
                .map(FileInfoDO::parseFileMsg)
                .collect(Collectors.toList());
    }

    public LoginInfoDTO getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(LoginInfoDTO accountInfo) {
        this.accountInfo = accountInfo;
    }

    public UserInfoDO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDO userInfo) {
        this.userInfo = userInfo;
    }

    public List<FileInfoDO.FileMsg> getCertificateFiles() {
        return certificateFiles;
    }

    public void setCertificateFiles(List<FileInfoDO.FileMsg> certificateFiles) {
        this.certificateFiles = certificateFiles;
    }
}
