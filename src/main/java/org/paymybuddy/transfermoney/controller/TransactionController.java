package org.paymybuddy.transfermoney.controller;

import jakarta.validation.Valid;
import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
                               Model model){
        /*User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }*/
        if (bindingResult.hasErrors()) {
            model.addAttribute("transaction", transaction);
            return "transfer";
        }

        return transactionService.saveTransaction(transaction);
    }
}
