package com.example.ebanking.controller;

import com.example.ebanking.model.AccountModel;
import com.example.ebanking.model.ClientModel;
import com.example.ebanking.model.DepositModel;
import com.example.ebanking.service.AccountService;
import com.example.ebanking.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DepositController {
    private final AccountService accountService;
    private final TransactionService transactionService;


    public DepositController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/alimentareCont")
    public String getPage(HttpSession session, Model model) {
        ClientModel client = (ClientModel) session.getAttribute("client");
        model.addAttribute("accounts",accountService.getActiveByClientId(client.getId()));
        return "alimentareCont";
    }

    @PostMapping("/alimentareCont")
    public void accountDeposit(@ModelAttribute DepositModel depositModel, Model model, HttpSession session) {
        transactionService.deposit(depositModel);
        model.addAttribute("successMessage","Contul alimentat cu succes!");
        getPage(session,model);
    }

    @PostMapping("/getSourceAccounts")
    public ResponseEntity<?> getAccounts(HttpSession session) {
        ClientModel client = (ClientModel) session.getAttribute("client");
        List<AccountModel> result = accountService.getActiveByClientId(client.getId());
        return ResponseEntity.ok(result);
    }
}
