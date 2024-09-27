package org.paymybuddy.transfermoney.controller;


import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.ContactDTO;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Slf4j
@Controller
public class ContactController {
    @Autowired
    ConnectionsService connectionsService;

    @Autowired
    ContactService contactService;

    /**
     *
     * To create a new message to contact the administrator
     *
     */
    @PostMapping("/contact/save")
    @Transactional
    public String newContact(@RequestParam("message") String message, Error error) {

        connectionsService.addMessage(message);

        return "redirect:/";
    }

    /**
     *
     * To access to the contact.html page
     *
     */
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
}
