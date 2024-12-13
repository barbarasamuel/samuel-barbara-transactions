package org.paymybuddy.transfermoney.controller;


import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    @Autowired
    ConnectionsService connectionsService;

    /*@Autowired
    ContactService contactService;*/

    /**
     *
     * To create a new message to contact the administrator
     *
     */
    @PostMapping("/contact/save")
    public String newContact(@RequestParam("message") String message) {

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
