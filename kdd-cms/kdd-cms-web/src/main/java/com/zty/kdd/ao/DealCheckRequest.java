package com.zty.kdd.ao;

import java.io.Serializable;

import com.zty.framework.dto.DataDTO;

/**
 * 进件审核请求参数类
 * @author tianyi
 * @date 2021-04-12 23:48
 */
public class DealCheckRequest extends DataDTO implements Serializable {

    private Integer accountId;

    /**
     * 是否通过
     */
    private Boolean isPass;

    /**
     * 审核描述
     */
    private String description;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Boolean getIsPass() {
        return isPass;
    }

    public void setIsPass(Boolean pass) {
        isPass = pass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
