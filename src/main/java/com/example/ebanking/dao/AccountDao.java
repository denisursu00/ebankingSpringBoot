package com.example.ebanking.dao;

import com.example.ebanking.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class AccountDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AccountModel> getAllByClientId(Integer clientId) {
        String sql = "SELECT " +
                "conturi.id, " +
                "conturi.nr_cont, " +
                "conturi.banca, " +
                "conturi.suma, " +
                "stari.stare " +
                "FROM conturi " +
                "    JOIN stari " +
                "    ON stari.id=conturi.id_stare " +
                "WHERE id_client=? ";
        return jdbcTemplate.query(sql,new Object[]{clientId},((resultSet, i) -> {
            AccountModel account = new AccountModel();
            account.setId(resultSet.getInt("id"));
            account.setAccountNumber(resultSet.getInt("nr_cont"));
            account.setBank(resultSet.getString("banca"));
            account.setSum(resultSet.getBigDecimal("suma"));
            account.setState(resultSet.getString("stare"));
            return account;
        }));
    }

    public List<AccountModel> getActiveByClientId(Integer clientId) {
        String sql = "SELECT " +
                "conturi.id, " +
                "conturi.nr_cont, " +
                "conturi.banca, " +
                "conturi.suma, " +
                "stari.stare " +
                "FROM conturi " +
                "    JOIN stari " +
                "    ON stari.id = conturi.id_stare " +
                "WHERE id_client = ? " +
                "AND stari.stare = 'ACTIV'";
        return jdbcTemplate.query(sql,new Object[]{clientId},((resultSet, i) -> {
           AccountModel account = new AccountModel();
           account.setId(resultSet.getInt("id"));
           account.setAccountNumber(resultSet.getInt("nr_cont"));
           account.setBank(resultSet.getString("banca"));
           account.setSum(resultSet.getBigDecimal("suma"));
           account.setState(resultSet.getString("stare"));
           return account;
        }));
    }

    public List<PendingAccountModel> getPending() {
        String sql = "SELECT " +
                "conturi.id, " +
                "conturi.nr_cont, " +
                "conturi.id_stare, " +
                "clienti.nume, " +
                "clienti.prenume " +
                "FROM conturi " +
                "    JOIN stari " +
                "    ON stari.id = conturi.id_stare " +
                "    JOIN clienti " +
                "    ON clienti.id = conturi.id_client " +
                "WHERE stari.stare = 'IN APROBARE'";
        return jdbcTemplate.query(sql,((resultSet, i) -> {
            PendingAccountModel account = new PendingAccountModel();
            account.setId(resultSet.getInt("id"));
            account.setAccountNumber(resultSet.getInt("nr_cont"));
            account.setClientName(resultSet.getString("nume")+" "+resultSet.getString("prenume"));
            account.setStateId(resultSet.getInt("id_stare"));
            return account;
        }));
    }

    public void updatePending(AccountApprobationModel approbationModel) {
        String sql = "" +
                "UPDATE " +
                "conturi " +
                "SET id_stare = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql,approbationModel.getState(),approbationModel.getAccountId());
    }

    public boolean exists(NewAccountModel accountModel) {
        String sql = "" +
                "SELECT " +
                "count(*) " +
                "FROM conturi " +
                "WHERE id_client = ? " +
                "AND nr_cont = ?";
        Integer nr = jdbcTemplate.queryForObject(sql,new Object[]{accountModel.getClientId(),accountModel.getAccountNumber()}, Integer.class);
        if (nr>0) {
            return true;
        } else {
            return false;
        }
    }
    public void insert(NewAccountModel accountModel) {
        String sql = "" +
                "INSERT " +
                "INTO conturi(id,nr_cont,banca,suma,id_stare,id_client) " +
                "VALUES(conturi_seq.nextval,?,'Banca Transilvania',0,(SELECT id FROM stari WHERE stare = 'IN APROBARE'),?)";
        jdbcTemplate.update(sql,accountModel.getAccountNumber(),accountModel.getClientId());
    }

    public void updateSum(Integer accountId, BigDecimal depositSum) {
        String sql = "" +
                "UPDATE " +
                "conturi " +
                "SET suma = suma + ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql,depositSum,accountId);
    }

    public BigDecimal getSumById(Integer accountId) {
        String sql = "" +
                "SELECT suma " +
                "FROM conturi " +
                "WHERE id = ? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{accountId}, BigDecimal.class);
    }

}
