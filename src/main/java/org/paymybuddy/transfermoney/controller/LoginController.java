package org.paymybuddy.transfermoney.controller;

import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model){

        //model.addAttribute("connection",connectionDTO);
        return "login";
    }

    @GetMapping("/")
    public String home(){return "transfer";}

}
