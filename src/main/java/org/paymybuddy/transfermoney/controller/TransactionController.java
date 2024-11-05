package org.paymybuddy.transfermoney.controller;

import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class TransactionController {
   /**/ @Autowired
    TransactionsService transactionsService;

    @Autowired
    ConnectionsService connectionsService;



    /**
     *
     * To add a transaction
     *
     */
   @PostMapping("/transactions/save")
   public String saveTransaction(@ModelAttribute TransactionForm transactionForm,
                                 Model model){

       List <TransactionDTO> transactionsList = connectionsService.saveTransaction(transactionForm);

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

       ManagePaginationDTO managePaginationDTO = connectionsService.managePagination(pageNo, sortField, sortDir);

       model.addAttribute("allConnectionsList", managePaginationDTO.getAllConnectionsDTOList());
       model.addAttribute("connectionsList", managePaginationDTO.getRelationsConnectionList());
       model.addAttribute("username", managePaginationDTO.getConnectionDTO().getName());
       model.addAttribute("debtorAccountList",managePaginationDTO.getBankAccountDTOList());
       model.addAttribute("currentPage", pageNo);
       model.addAttribute("totalPages", managePaginationDTO.getPage().getTotalPages());
       model.addAttribute("totalItems", managePaginationDTO.getPage().getTotalElements());
       model.addAttribute("transactionsList", managePaginationDTO.getTransactionsDTOList());

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

        TransferPageDTO transferPageDTO = connectionsService.accessTransferPage();

        model.addAttribute("allConnectionsList", transferPageDTO.getAllConnectionsDTOList());
        model.addAttribute("connectionsList", transferPageDTO.getRelationsConnectionList());
        model.addAttribute("transactionsList", transferPageDTO.getTransactionsConnectionList());/**/
        model.addAttribute("username", transferPageDTO.getConnectionDTO().getName());
        model.addAttribute("debtorAccountList",transferPageDTO.getBankAccountDTOList());

        return findPaginated(1, "transactionDate", "desc", model);
    }


}
