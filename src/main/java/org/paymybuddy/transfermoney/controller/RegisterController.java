package org.paymybuddy.transfermoney.controller;

import jakarta.validation.Valid;
import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class RegisterController {
    @Autowired
    ConnectionsService connectionsService;
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("connection") ConnectionDTO connection,
                               BindingResult bindingResult,
                               Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("connection", connection);
            return "register";
        }

        if(Objects.equals(connectionsService.newConnection(connection), "There is already an account registered with that email")) {
            bindingResult.rejectValue("email", null, "There is already an account registered with that email");
        }

        return "redirect:/register?success";
    }
}
