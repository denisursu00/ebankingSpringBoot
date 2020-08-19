package com.example.ebanking.model;

import java.math.BigDecimal;

public class AccountModel {
    private Integer id;
    private Integer accountNumber;
    private String bank;
    private BigDecimal sum;
    private String state;
    private Integer clientId;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }


    public Integer getAccountNumber() { return accountNumber; }
    public void setAccountNumber(Integer accountNumber) { this.accountNumber = accountNumber; }


    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }


    public BigDecimal getSum() { return sum; }
    public void setSum(BigDecimal sum) { this.sum = sum; }


    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public Integer getClientId() { return clientId; }

    public void setClientId(Integer clientId) { this.clientId = clientId; }
}
