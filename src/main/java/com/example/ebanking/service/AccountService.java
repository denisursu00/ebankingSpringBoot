package com.example.ebanking.service;

import com.example.ebanking.ValidationException;
import com.example.ebanking.dao.AccountDao;
import com.example.ebanking.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private final AccountDao accountDao;

    @Autowired
    public AccountService(AccountDao accountDao) { this.accountDao = accountDao; }

    public List<AccountModel> getAllByClientId(Integer clientId) {
        return accountDao.getAllByClientId(clientId);
    }

    public List<AccountModel> getActiveByClientId(Integer clientId) {
        return accountDao.getActiveByClientId(clientId);
    }

    public List<PendingAccountModel> getPending() {
        return accountDao.getPending();
    }

    public void updatePending(AccountApprobationModel approbationModel) {
        accountDao.updatePending(approbationModel);
    }

    public void insert(NewAccountModel accountModel) throws ValidationException {
        if (accountDao.exists(accountModel)) {
            throw new ValidationException("Account cu numarul dat deja exista!");
        } else {
            accountDao.insert(accountModel);
        }
    }

    public void updateSum(Integer accountId, BigDecimal depositSum) {
        accountDao.updateSum(accountId,depositSum);
    }

    public BigDecimal getSumById(Integer accountId) {
        return accountDao.getSumById(accountId);
    }

}
