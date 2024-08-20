package org.paymybuddy.transfermoney.controller;


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

@Controller
public class ContactController {
    @Autowired
    ConnectionsService connectionsService;

    @Autowired
    ContactService contactService;

    @PostMapping("/contact/save")
    @Transactional
    public String newContact(@RequestParam("message") String message, Error error) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());

        ContactDTO contactDTO = ContactDTO.builder()
                .sender(connectionDTO)
                .message(message)
                .build();

        contactService.addedMessage(contactDTO);

        return "redirect:/";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
}
