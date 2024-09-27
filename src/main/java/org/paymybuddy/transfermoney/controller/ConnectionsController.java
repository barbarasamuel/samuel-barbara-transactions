package org.paymybuddy.transfermoney.controller;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.service.BankAccountService;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@Controller
public class ConnectionsController {
    @Autowired
    private ConnectionsService connectionsService;
    @Autowired
    private RelationService relationService;
    @Autowired
    private BankAccountService bankAccountService;


    /**
     *
     * To add a relation
     *
     */
    @PostMapping("/connection/list")
    public String addNewConnection( @RequestParam("friendName") String friendName, Model model) {

        ConnectionDTO connectionDTO = connectionsService.addConnection(friendName);

        List<RelationsConnection> connectionsList = relationService.getRelations(connectionDTO);
        model.addAttribute("connectionsList",connectionsList);
        return "redirect:/";
    }
}
