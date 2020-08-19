package com.example.ebanking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("clientModel")
public class HomeController {

    @GetMapping({"/","/home"})
    public String getPage() {
        return "home";
    }
}
