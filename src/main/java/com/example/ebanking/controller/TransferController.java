package com.example.ebanking.controller;

import com.example.ebanking.model.AccountModel;
import com.example.ebanking.model.ClientModel;
import com.example.ebanking.model.TransferModel;
import com.example.ebanking.service.AccountService;
import com.example.ebanking.service.ClientService;
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
public class TransferController {
    private AccountService accountService;
    private ClientService clientService;
    private TransactionService transactionService;

    public TransferController(AccountService accountService,ClientService clientService,TransactionService transactionService) {
        this.accountService = accountService;
        this.clientService = clientService;
        this.transactionService = transactionService;
    }

    @GetMapping("/transfer")
    public String getPage(HttpSession session, Model model) {
        ClientModel client = (ClientModel) session.getAttribute("client");
        model.addAttribute("sourceAccounts",accountService.getActiveByClientId(client.getId()));
        model.addAttribute("clients",clientService.getAll());
        return "transfer";
    }

    @PostMapping("/transfer")
    public void accountsTransfer(@ModelAttribute TransferModel transferModel,HttpSession session, Model model) {
        transactionService.transfer(transferModel);
        model.addAttribute("successMessage","Transferul efectuat cu succes!");
        getPage(session,model);
    }

    @PostMapping("/getDestinationAccounts")
    public ResponseEntity<?> getAccounts(Integer clientId) {
        List<AccountModel> result = accountService.getActiveByClientId(clientId);
        return ResponseEntity.ok(result);
    }

}
