package org.paymybuddy.transfermoney.controller;

import jakarta.transaction.Transactional;
import org.paymybuddy.transfermoney.model.BankAccountDTO;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.model.TransactionForm;
import org.paymybuddy.transfermoney.service.BankAccountService;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionController {
    @Autowired
    TransactionsService transactionService;

    @Autowired
    ConnectionsService connectionsService;

    @Autowired
    BankAccountService bankAccountService;

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
    /*@GetMapping("/")
    public String printTransaction(Model model){
        model.addAttribute("transactionForm", new TransactionForm());
        return "transferTest";
    }*/

    /**
     *
     * To add a transaction
     *
     */
       @PostMapping("/transactions/save")
       @Transactional
       /*public String saveTransaction(@RequestParam("newTransaction") Map<String,String> newTransaction,
                                     Model model){
       public String saveTransaction(@RequestParam("newTransaction") TransactionForm transactionForm,
                                     Model model){*/
       public String saveTransaction(@ModelAttribute TransactionForm transactionForm,
                                     Model model){
        /*User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
           if (bindingResult.hasErrors()) {
               model.addAttribute("transaction", transactionForm);
               return "transfer";
           }*/

           //ConnectionDTO creditorDTO = connectionsService.getConnection(newTransaction.get("name"));
           ConnectionDTO creditorDTO = connectionsService.getCreditor(transactionForm.getId());

           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           UserDetails userDetails = (UserDetails) authentication.getPrincipal();
           ConnectionDTO debtorDTO = connectionsService.getIdentifiant(userDetails.getUsername());

           /*TransactionDTO transactionDTO = TransactionDTO.builder()
                   .creditor(creditorDTO)
                   .debtor(debtorDTO)
                   .description(newTransaction.get("description"))
                   .amount(Double.valueOf(newTransaction.get("amount")))
                   .build();*/

           TransactionDTO transactionDTO = TransactionDTO.builder()
                   .creditor(creditorDTO)
                   .debtor(debtorDTO)
                   .description(transactionForm.getDescription())
                   .amount(transactionForm.getAmount())
                   .transactionDate(new Date())
                   .build();

           TransactionDTO newTransactionDTO = transactionService.saveTransaction(transactionDTO);

           BankAccountDTO debtorAccountDTO = bankAccountService.getConnectionAccount(debtorDTO);
           Double updatedDebtorBalance = bankAccountService.updateDebtorAccount(debtorAccountDTO,transactionForm.getAmount());

           debtorAccountDTO.setConnectionBankAccount(debtorDTO);
           debtorAccountDTO.setBalance(updatedDebtorBalance);
           bankAccountService.saveBankAccount(debtorAccountDTO);

           BankAccountDTO creditorAccountDTO = bankAccountService.getConnectionAccount(creditorDTO);
           Double updatedCreditorBalance = bankAccountService.updateCreditorAccount(creditorAccountDTO,transactionForm.getAmount());

           creditorAccountDTO.setConnectionBankAccount(creditorDTO);
           creditorAccountDTO.setBalance(updatedCreditorBalance);
           bankAccountService.saveBankAccount(creditorAccountDTO);

           List <TransactionDTO> transactionsList =  transactionService.getTransactions(debtorDTO.getId());

           model.addAttribute("transactionsList", transactionsList);
           return  "redirect:/";
       }

    @GetMapping("/transfer")
    public String transactionPage( Model model, Principal principal){


        //List<TransactionDTO> transactionDTOList = transactionService.listTransactions();
        //model.addAttribute("transactionsList",transactionDTOList);
        long idUser = 1;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //List<ConnectionDTO> connectionsList = connectionService.getConnections(userDetails.getUsername());
        List<TransactionDTO> transactionsList = transactionService.getTransactions(idUser);
        //model.addAttribute("connectionsList", connectionsList);
        model.addAttribute("transactionsList", transactionsList);

        model.addAttribute("transactionForm",new TransactionForm());
        return "transferTest";
    }

   /* @GetMapping("/displayConnectionsList")
    public String displayConnectionsList(Model model,@ModelAttribute("user") ConnectionDTO connectionDTO){
        List<ConnectionDTO> connectionsList = transactionService.getConnections(connectionDTO);

        model.addAttribute("connectionsList", connectionsList);

        return "transfer";
    }*/
}
