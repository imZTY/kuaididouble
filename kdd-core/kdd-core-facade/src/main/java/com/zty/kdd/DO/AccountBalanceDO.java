package com.zty.kdd.DO;

public class AccountBalanceDO {
    private Integer accountId;

    private Long totalBalance;

    private Long availableBalance;

    private Long frozenBalance;

    private String preSalty;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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