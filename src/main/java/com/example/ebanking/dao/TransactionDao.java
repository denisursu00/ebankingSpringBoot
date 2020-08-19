package com.example.ebanking.dao;

import com.example.ebanking.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Repository
public class TransactionDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionDao(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    public void deposit(DepositModel depositModel) {
        String sql = "" +
                "INSERT " +
                "INTO tranzactii(id,data_operatie,id_cont_sursa,sold_final_sursa,id_cont_destinatie,sold_final_destinatie,suma_tranzactie) " +
                "VALUES(tranzactii_seq.NEXTVAL,CURRENT_DATE,?,?,?,?,?)";
        Integer accountId = depositModel.getAccountId();
        BigDecimal accountSum = depositModel.getAccountSum();
        BigDecimal depositSum = depositModel.getDepositSum();
        jdbcTemplate.update(sql,accountId,accountSum,accountId,accountSum,depositSum);
    }

    public void transfer(TransferModel transferModel) {
        String sql = "" +
                "INSERT " +
                "INTO tranzactii(id,data_operatie,id_cont_sursa,sold_final_sursa,id_cont_destinatie,sold_final_destinatie,suma_tranzactie) " +
                "VALUES(tranzactii_seq.NEXTVAL,CURRENT_DATE,?,?,?,?,?)";
        Integer sourceAccountId = transferModel.getSourceAccountId();
        BigDecimal sourceAccountSum = transferModel.getSourceAccountSum();
        Integer destinationAccountId = transferModel.getDestinationAccountId();
        BigDecimal destinationAccountSum = transferModel.getDestinationAccountSum();
        BigDecimal transferSum = transferModel.getTransferSum();
        jdbcTemplate.update(sql,sourceAccountId,sourceAccountSum,destinationAccountId,destinationAccountSum,transferSum);
    }

    public List<TransactionModel> getByAccountId(ReportModel reportModel) {
        DateTimeFormatter toFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String sql = "" +
                "SELECT " +
                "data_operatie," +
                "id_cont_destinatie AS cont, " +
                "suma_tranzactie AS suma_debit, " +
                "NULL AS suma_credit, " +
                "sold_final_sursa AS sold_curent " +
                "FROM tranzactii " +
                "WHERE (id_cont_sursa = ?) AND (id_cont_destinatie<>?) AND (data_operatie BETWEEN ? AND ?) " +
                "UNION " +
                "SELECT " +
                "data_operatie, " +
                "id_cont_sursa AS cont, " +
                "NULL AS suma_debit, " +
                "suma_tranzactie AS suma_credit, " +
                "sold_final_destinatie AS sold_curent " +
                "FROM tranzactii " +
                "WHERE (id_cont_destinatie = ?) AND (data_operatie BETWEEN ? AND ?) " +
                "ORDER BY data_operatie ASC";
        Integer accountId = reportModel.getAccountId();
        Date startDate = reportModel.getStartDate();
        Date endDate = reportModel.getEndDate();
        return jdbcTemplate.query(sql,new Object[]{accountId,accountId,startDate,endDate,accountId,startDate,endDate},((resultSet, i) -> {
            TransactionModel transaction = new TransactionModel();

            LocalDate transactionDate = resultSet.getObject("data_operatie",LocalDate.class);

            transaction.setTransactionDate(transactionDate.format(toFormat));
            transaction.setAccountId(resultSet.getInt("cont"));
            transaction.setDebitSum(resultSet.getBigDecimal("suma_debit"));
            transaction.setCreditSum(resultSet.getBigDecimal("suma_credit"));
            transaction.setCurrentSum(resultSet.getBigDecimal("sold_curent"));

            return transaction;
        }));
    }

}
