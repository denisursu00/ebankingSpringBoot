package com.example.ebanking.controller;

import com.example.ebanking.model.AccountApprobationModel;
import com.example.ebanking.model.PendingAccountModel;
import com.example.ebanking.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApprobationController {
    private final AccountService accountService;

    public ApprobationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/aprobare")
    public String getPage(Model model) {
        model.addAttribute("pendingAccounts",accountService.getPending());
        return "aprobare";
    }

    @PostMapping("/aprobare")
    public void updateAccounts(@ModelAttribute AccountApprobationModel account) {
        accountService.updatePending(account);
    }

}
