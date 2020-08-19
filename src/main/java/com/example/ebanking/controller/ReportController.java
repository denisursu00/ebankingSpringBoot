package com.example.ebanking.controller;

import com.example.ebanking.PdfGenerator;
import com.example.ebanking.model.ClientModel;
import com.example.ebanking.model.ReportModel;
import com.example.ebanking.model.TransactionModel;
import com.example.ebanking.service.AccountService;
import com.example.ebanking.service.TransactionService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
public class ReportController {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private List<TransactionModel> transactions;

    public ReportController(AccountService accountService,TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/rapoarte")
    public String getPage(HttpSession session, Model model) {
        ClientModel client = (ClientModel) session.getAttribute("client");
        model.addAttribute("accounts", accountService.getActiveByClientId(client.getId()));
        return "rapoarte";
    }

    @PostMapping("/rapoarte")
    public void getTransactions(@ModelAttribute ReportModel reportModel, HttpSession session, Model model) {
        transactions = transactionService.getByAccountId(reportModel);
        model.addAttribute("transactions",transactions);
        getPage(session,model);
    }

    @GetMapping("/getCsvReport")
    public void exportCsv(HttpServletResponse response) throws Exception {
        String fileName = "report.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + fileName + "\"");

        StatefulBeanToCsv<TransactionModel> writer = new StatefulBeanToCsvBuilder<TransactionModel>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();
        writer.write(transactions);

    }

    @GetMapping("/getPdfReport")
    public ResponseEntity<Resource> exportPdf() {

            ByteArrayInputStream bis = PdfGenerator.getPdf(transactions);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=report.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
    }

}
