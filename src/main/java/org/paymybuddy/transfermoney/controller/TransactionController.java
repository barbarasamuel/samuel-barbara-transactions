package org.paymybuddy.transfermoney.controller;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
       public String saveTransaction(@ModelAttribute TransactionForm transactionForm,
                                     Model model){

           ConnectionDTO debtorDTO = connectionsService.saveTransaction(transactionForm);

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

       log.info("Page or sort modified");
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
