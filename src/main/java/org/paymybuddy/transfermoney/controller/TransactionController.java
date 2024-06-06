package org.paymybuddy.transfermoney.controller;

import jakarta.validation.Valid;
import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TransactionController {
    @Autowired
    TransactionsService transactionService;

    /*@GetMapping("/transfer")
    public String saveConnection(Model model){
        model.addAttribute("connection",new ConnectionsEntity());
        return "transfer";
    }*/
    @PostMapping("/transfer/save")
    public String transaction(@Valid @ModelAttribute("transaction") TransactionDTO transaction,
                               BindingResult bindingResult,
                               Model model,@AuthenticationPrincipal UserDetails userDetails){
        /*User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }*/
        if (bindingResult.hasErrors()) {
            model.addAttribute("transaction", transaction);
            return "transfer";
        }

        return "";//transactionService.saveTransaction(transaction);
    }
       /* @PostMapping("/transfer/save")
    public ModelAndView saveTransaction(@ModelAttribute TransactionDTO transaction) {
        transactionService.saveTransaction(transaction);
        return new ModelAndView("redirect:/");
    }*/
       @PostMapping("/saveTransaction")
       public String saveTransaction(@Valid @ModelAttribute("transaction") TransactionDTO transaction,BindingResult bindingResult,
                                     Model model){
        /*User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }*/
           if (bindingResult.hasErrors()) {
               model.addAttribute("transaction", transaction);
               return "transfer";
           }

           transactionService.saveTransaction(transaction);
           return "transfer";
       }

    @GetMapping("/displayConnectionsList")
    public String displayConnectionsList(Model model,@ModelAttribute("user") ConnectionDTO connectionDTO){
        List<ConnectionDTO> connectionsList = transactionService.getConnections(connectionDTO);

        model.addAttribute("connectionsList", connectionsList);

        return "transfer";
    }
}
