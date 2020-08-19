package com.example.ebanking.service;

import com.example.ebanking.dao.TransactionDao;
import com.example.ebanking.model.DepositModel;
import com.example.ebanking.model.ReportModel;
import com.example.ebanking.model.TransactionModel;
import com.example.ebanking.model.TransferModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
    TransactionDao transactionDao;
    AccountService accountService;

    @Autowired
    public TransactionService(TransactionDao transactionDao,AccountService accountService) {
        this.transactionDao = transactionDao;
        this.accountService = accountService;
    }

    public void deposit(DepositModel depositModel) {
        BigDecimal accountSum = depositModel.getAccountSum();
        BigDecimal depositSum = depositModel.getDepositSum();
        depositModel.setAccountSum(accountSum.add(depositSum));
        accountService.updateSum(depositModel.getAccountId(),depositModel.getDepositSum());
        transactionDao.deposit(depositModel);
    }

    public void transfer(TransferModel transferModel) {
        accountService.updateSum(transferModel.getSourceAccountId(),transferModel.getTransferSum().negate());
        transferModel.setSourceAccountSum(accountService.getSumById(transferModel.getSourceAccountId()));

        accountService.updateSum(transferModel.getDestinationAccountId(),transferModel.getTransferSum());
        transferModel.setDestinationAccountSum(accountService.getSumById(transferModel.getDestinationAccountId()));

        transactionDao.transfer(transferModel);
    }

    public List<TransactionModel> getByAccountId(ReportModel reportModel) {
        return transactionDao.getByAccountId(reportModel);
    }

}
