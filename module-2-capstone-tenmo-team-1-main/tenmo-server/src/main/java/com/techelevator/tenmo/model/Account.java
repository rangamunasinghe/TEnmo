package com.techelevator.tenmo.model;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class Account {
    private Long accountId;
    private long userId;
    @DecimalMin(value = "0.00", inclusive = false, message = "Balance amount cannot drop below zero")
    private BigDecimal accountBalance;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Account(Long accountId, long userId, String accountName, BigDecimal accountBalance) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountBalance = BigDecimal.ZERO;
    }

    public Account() {

    }
}
