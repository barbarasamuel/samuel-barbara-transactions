package org.paymybuddy.transfermoney.controller;

import jakarta.validation.Valid;
import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.RegisterForm;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.model.TransactionForm;
import org.paymybuddy.transfermoney.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
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
    /*@PostMapping("/transfer/save")
    public String transaction(@Valid @ModelAttribute("transactionForm") TransactionForm transactionForm,
                               BindingResult bindingResult,
                               Model model,@AuthenticationPrincipal UserDetails userDetails){*/
        /*User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }*/
        /*if (bindingResult.hasErrors()) {
            model.addAttribute("transaction", transactionForm);
            return "transfer";
        }

        return "transfer";//transactionService.saveTransaction(transaction);
    }*/
       /* @PostMapping("/transfer/save")
    public ModelAndView saveTransaction(@ModelAttribute TransactionDTO transaction) {
        transactionService.saveTransaction(transaction);
        return new ModelAndView("redirect:/");
    }*/
       @PostMapping("/transaction/save")
       public String saveTransaction(@ModelAttribute("transaction") TransactionForm transactionForm,
                                     Model model){
        /*User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
           if (bindingResult.hasErrors()) {
               model.addAttribute("transaction", transactionForm);
               return "transfer";
           }*/

           TransactionDTO transactionDTO = TransactionDTO.builder()
                   .connection(transactionForm.getConnection())
                   .description(transactionForm.getDescription())
                   .amount(transactionForm.getAmount())
                   .build();

           transactionService.saveTransaction(transactionDTO);

           TransactionForm newTransactionForm = new TransactionForm();
           model.addAttribute("transaction", newTransactionForm);
           return "transfer";
       }

    @GetMapping("/transfer")
    public String transactionPage(Model model, Principal principal){
        List<TransactionDTO> transactionDTOList = transactionService.listTransactions(principal.getName());
        model.addAttribute("transactionsList",transactionDTOList);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<ConnectionDTO> connectionsList = transactionService.getConnections(userDetails.getUsername());
        model.addAttribute("connectionsList", connectionsList);

        model.addAttribute("transactionForm",new TransactionForm());
        return "transfer";
    }

    /*@GetMapping("/displayConnectionsList")
    public String displayConnectionsList(Model model,@ModelAttribute("user") ConnectionDTO connectionDTO){
        List<ConnectionDTO> connectionsList = transactionService.getConnections(connectionDTO);

        model.addAttribute("connectionsList", connectionsList);

        return "transfer";
    }*/
}
