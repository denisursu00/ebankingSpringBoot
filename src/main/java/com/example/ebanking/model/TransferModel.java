package com.example.ebanking.model;

import java.math.BigDecimal;

public class TransferModel {
    private Integer sourceAccountId;
    private BigDecimal sourceAccountSum;
    private Integer destinationAccountId;
    private BigDecimal destinationAccountSum;
    private BigDecimal transferSum;

    public Integer getSourceAccountId() { return sourceAccountId; }
    public void setSourceAccountId(Integer sourceAccountId) { this.sourceAccountId = sourceAccountId; }


    public BigDecimal getSourceAccountSum() { return sourceAccountSum; }
    public void setSourceAccountSum(BigDecimal sourceAccountSum) { this.sourceAccountSum = sourceAccountSum; }


    public Integer getDestinationAccountId() { return destinationAccountId; }
    public void setDestinationAccountId(Integer destinationAccountId) { this.destinationAccountId = destinationAccountId; }


    public BigDecimal getDestinationAccountSum() { return destinationAccountSum; }
    public void setDestinationAccountSum(BigDecimal destinationAccountSum) { this.destinationAccountSum = destinationAccountSum; }


    public BigDecimal getTransferSum() { return transferSum; }
    public void setTransferSum(BigDecimal transferSum) { this.transferSum = transferSum; }
}
