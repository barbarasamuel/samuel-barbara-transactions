package org.paymybuddy.transfermoney.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.RelationService;
import org.paymybuddy.transfermoney.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    TransactionsService transactionsService;
    @Autowired
    RelationService relationService;
    @Autowired
    ConnectionsService connectionsService;

    @GetMapping("/login")
    public String login(Model model){

        //model.addAttribute("connection",connectionDTO);
        return "login";
    }


    @PostMapping("/process-login")
    /*public String handleLogin(){

    }*/
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpServletRequest request){
        try{
            request.login(username,password);
            return "redirect:/";
        }catch(ServletException e){
            return "login";
        }
    }
    @GetMapping("/")
    public String home(Model model, Principal principal){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());
        List<RelationsConnection> connectionsList = relationService.getRelations(connectionDTO);
        List<TransactionsConnection> transactionsList = transactionsService.getTransactionsFromUser(connectionDTO);

        model.addAttribute("connectionsList", connectionsList);
        model.addAttribute("transactionsList", transactionsList);/**/
        model.addAttribute("username", connectionDTO.getName());

        return "transferTest";
        //return "test";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/login?logout";
    }
}
