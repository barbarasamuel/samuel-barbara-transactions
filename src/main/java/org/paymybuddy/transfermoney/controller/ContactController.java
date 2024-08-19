package org.paymybuddy.transfermoney.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.paymybuddy.transfermoney.model.RegisterForm;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {
    @Autowired
    ConnectionsService connectionsService;

    /*@PostMapping("/contact/save")
    @Transactional
    public String newContact(@Valid @ModelAttribute("contactForm") ContactForm contactForm,
                               BindingResult bindingResult,
                               Model model){

        return "contact";
    }*/
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
}
