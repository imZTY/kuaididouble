package com.zty.kdd.DO;

import com.zty.framework.dto.DataDTO;

public class AccountBalanceDO extends DataDTO {
    private Integer id;

    private Integer accountId;

    private Integer productId;

    private Long totalBalance;

    private Long availableBalance;

    private Long frozenBalance;

    private String preSalty;

    public AccountBalanceDO accountId(Integer accountId) {
        setAccountId(accountId);
        return this;
    };

    public AccountBalanceDO productId(Integer productId) {
        setProductId(productId);
        return this;
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Long totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Long getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(Long frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public String getPreSalty() {
        return preSalty;
    }

    public void setPreSalty(String preSalty) {
        this.preSalty = preSalty;
    }
}