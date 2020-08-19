package com.example.ebanking.model;

import java.math.BigDecimal;

public class DepositModel {
    private Integer accountId;
    private BigDecimal accountSum;
    private BigDecimal depositSum;


    public Integer getAccountId() { return accountId; }
    public void setAccountId(Integer accountId) { this.accountId = accountId; }


    public BigDecimal getAccountSum() { return accountSum; }
    public void setAccountSum(BigDecimal accountSum) { this.accountSum = accountSum; }


    public BigDecimal getDepositSum() { return depositSum; }
    public void setDepositSum(BigDecimal depositSum) { this.depositSum = depositSum; }
}
