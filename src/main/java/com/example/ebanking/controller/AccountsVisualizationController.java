package com.example.ebanking.controller;

import com.example.ebanking.model.ClientModel;
import com.example.ebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AccountsVisualizationController {

    private AccountService accountService;

    @Autowired
    public AccountsVisualizationController(AccountService accountService) { this.accountService = accountService; }

    @GetMapping("/vizualizareConturi")
    public String getPage(HttpSession session, Model model) {
        ClientModel client = (ClientModel) session.getAttribute("client");
        model.addAttribute("accounts",accountService.getAllByClientId(client.getId()));
        return "vizualizareConturi";
    }

}