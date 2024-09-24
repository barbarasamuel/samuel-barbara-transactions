package org.paymybuddy.transfermoney.controller;

import jakarta.transaction.Transactional;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;


@Controller
public class TransactionController {
   /**/ @Autowired
    TransactionsService transactionsService;

    @Autowired
    ConnectionsService connectionsService;

    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    RelationService relationService;


    /**
     *
     * To add a transaction
     *
     */
       @PostMapping("/transactions/save")
       @Transactional
       public String saveTransaction(@ModelAttribute TransactionForm transactionForm,
                                     Model model){

           ConnectionDTO creditorDTO = connectionsService.getCreditor(transactionForm.getId());

           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           UserDetails userDetails = (UserDetails) authentication.getPrincipal();
           ConnectionDTO debtorDTO = connectionsService.getIdentifiant(userDetails.getUsername());

           TransactionDTO transactionDTO = TransactionDTO.builder()
                   .creditor(creditorDTO)
                   .debtor(debtorDTO)
                   .description(transactionForm.getDescription())
                   .amount(transactionForm.getAmount())
                   .transactionDate(new Date())
                   .build();

           TransactionDTO newTransactionDTO = transactionsService.saveTransaction(transactionDTO);

           BankAccountDTO debtorAccountDTO = bankAccountService.getConnectionAccount(transactionForm.getIdDebtorAccount());
           Double updatedDebtorBalance = bankAccountService.updateDebtorAccount(debtorAccountDTO,transactionForm.getAmount());

           debtorAccountDTO.setId(transactionForm.getIdDebtorAccount());
           debtorAccountDTO.setConnectionBankAccount(debtorDTO);
           debtorAccountDTO.setBalance(updatedDebtorBalance);
           bankAccountService.saveBankAccount(debtorAccountDTO);

           BankAccountDTO creditorAccountDTO = bankAccountService.getConnectionAccount(transactionForm.getIdCreditorAccount());
           Double updatedCreditorBalance = bankAccountService.updateCreditorAccount(creditorAccountDTO,transactionForm.getAmount());

           creditorAccountDTO.setId(transactionForm.getIdCreditorAccount());
           creditorAccountDTO.setConnectionBankAccount(creditorDTO);
           creditorAccountDTO.setBalance(updatedCreditorBalance);
           bankAccountService.saveBankAccount(creditorAccountDTO);

           List <TransactionDTO> transactionsList =  transactionsService.getTransactions(debtorDTO.getId());

           model.addAttribute("transactionsList", transactionsList);
           return  "redirect:/";
       }

    @GetMapping("/transfer")
    public String transactionPage( Model model, Principal principal){

        return "transferTest";
    }

    /**
     *
     * To manage the pagination of the transactions table
     *
     */
   @GetMapping("/page/{pageNo}")
   public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model) {
       int pageSize = 3;


       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       UserDetails userDetails = (UserDetails) authentication.getPrincipal();
       ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());

       Page<Transactions> page = transactionsService.findPaginated(connectionDTO, pageNo, pageSize, sortField, sortDir);
       List < Transactions > transactionsList = page.getContent();

       List<ConnectionDTO> allConnectionsList = connectionsService.getAllConnections();
       List<RelationsConnection> connectionsList = relationService.getRelations(connectionDTO);
       List<BankAccountDTO> bankAccountDTOList = bankAccountService.getUserAccountsList(connectionDTO);

       model.addAttribute("allConnectionsList", allConnectionsList);
       model.addAttribute("connectionsList", connectionsList);
       model.addAttribute("username", connectionDTO.getName());
       model.addAttribute("debtorAccountList",bankAccountDTOList);
       model.addAttribute("currentPage", pageNo);
       model.addAttribute("totalPages", page.getTotalPages());
       model.addAttribute("totalItems", page.getTotalElements());
       model.addAttribute("transactionsList", transactionsList);

       model.addAttribute("sortField", sortField);
       model.addAttribute("sortDir", sortDir);
       model.addAttribute("reverseSortDir", sortDir.equals("desc") ? "asc" : "desc");

       return  "transferTest";
   }

    /**
     *
     * To access to the transferTest.html page and load it
     *
     */
    @GetMapping("/")
    public String home (Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());
        List<ConnectionDTO> allConnectionsList = connectionsService.getAllConnections();
        List<RelationsConnection> connectionsList = relationService.getRelations(connectionDTO);
        List<TransactionsConnection> transactionsList = transactionsService.getTransactionsFromUser(connectionDTO);
        List<BankAccountDTO> bankAccountDTOList = bankAccountService.getUserAccountsList(connectionDTO);

        model.addAttribute("allConnectionsList", allConnectionsList);
        model.addAttribute("connectionsList", connectionsList);
        model.addAttribute("transactionsList", transactionsList);/**/
        model.addAttribute("username", connectionDTO.getName());
        model.addAttribute("debtorAccountList",bankAccountDTOList);

        return findPaginated(1, "transactionDate", "desc", model);
    }


}
