package com.example.ebanking.controller;

import com.example.ebanking.ValidationException;
import com.example.ebanking.model.ClientModel;
import com.example.ebanking.model.NewAccountModel;
import com.example.ebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AccountsAdministrationController {

    private AccountService accountService;

    @Autowired
    public AccountsAdministrationController(AccountService accountService) { this.accountService = accountService; }

    @GetMapping("/administrareConturi")
    public String getPage(HttpSession session, Model model) {
        ClientModel client = (ClientModel) session.getAttribute("client");
        model.addAttribute("activeAccounts",accountService.getActiveByClientId(client.getId()));
        return "administrareConturi";
    }

    @PostMapping("/administrareConturi")
    public void newAccount(@ModelAttribute NewAccountModel accountModel, Model model, HttpSession session) {
        ClientModel client = (ClientModel) session.getAttribute("client");
        try {
            accountModel.setClientId(client.getId());
            accountService.insert(accountModel);
            model.addAttribute("messageSuccess", "Solicitarea transmisa cu succes!");
        } catch (ValidationException ex) {
            model.addAttribute("messageFail", ex.getMessage());
        }
        getPage(session,model);
    }

}
