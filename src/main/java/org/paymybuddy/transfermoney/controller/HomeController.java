package org.paymybuddy.transfermoney.controller;

import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("connection",new ConnectionsEntity());
        return "transfer";
    }
}
