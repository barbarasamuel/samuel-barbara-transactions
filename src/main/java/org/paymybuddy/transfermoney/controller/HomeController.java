package org.paymybuddy.transfermoney.controller;

import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Model model){

        //model.addAttribute("connection",connectionDTO);
        return "home";
    }

    @GetMapping("/connectionUser")
    public String home(Model model,@ModelAttribute("connectionUser") ConnectionDTO connectionDTO){

        model.addAttribute("connection",connectionDTO.getIdConnection());
        return "transfer";
    }
}
