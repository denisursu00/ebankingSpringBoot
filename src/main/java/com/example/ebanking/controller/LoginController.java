package com.example.ebanking.controller;

import com.example.ebanking.ValidationException;
import com.example.ebanking.model.ClientModel;
import com.example.ebanking.model.LoginModel;
import com.example.ebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@SessionAttributes("client")
public class LoginController {

    private final ClientService clientService;

    @Autowired
    public LoginController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/login")
    public RedirectView loginPost(@ModelAttribute LoginModel login, Model model) {
        try {
            ClientModel clientModel = clientService.getByUsernameAndPassword(login);
            model.addAttribute("client",clientModel);
        } catch (ValidationException ex) {
            model.addAttribute("message", ex.getMessage());
            return null;
        }
        return new RedirectView("/home");
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
}
