package com.example.ebanking.model;

public class NewAccountModel {
    private Integer accountNumber;
    private Integer clientId;

    public Integer getAccountNumber() { return accountNumber; }
    public void setAccountNumber(Integer accountNumber) { this.accountNumber = accountNumber; }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
