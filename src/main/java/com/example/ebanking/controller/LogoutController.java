package com.example.ebanking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    @GetMapping("/logout")
    public RedirectView getLogout(HttpSession session) {
        session.invalidate();
        return new RedirectView("/login");
    }
}
