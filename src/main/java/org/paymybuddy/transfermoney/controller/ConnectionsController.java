package org.paymybuddy.transfermoney.controller;


import org.paymybuddy.transfermoney.model.RelationsConnection;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ConnectionsController {
    @Autowired
    private ConnectionsService connectionsService;
    /*@Autowired
    private RelationService relationService;
    @Autowired
    private BankAccountService bankAccountService;*/


    /**
     *
     * To add a relation
     *
     */
    @PostMapping("/connection/list")
    public String addNewConnection( @RequestParam("friendName") String friendName, Model model) {

        List<RelationsConnection> connectionsList = connectionsService.addConnection(friendName);

        model.addAttribute("connectionsList",connectionsList);
        return "redirect:/";
    }
}
