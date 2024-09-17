package org.paymybuddy.transfermoney.controller;

import jakarta.transaction.Transactional;
import org.hibernate.Transaction;
//import org.hibernate.query.Page;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TransactionController {
   /**/ @Autowired
    TransactionsService transactionsServiceImpl;

    @Autowired
    ConnectionsService connectionsService;

    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    RelationService relationService;

    //TransactionsService transactionsServiceImpl = (TransactionsService) new TransactionsServiceImpl();
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

           TransactionDTO newTransactionDTO = transactionsServiceImpl.saveTransaction(transactionDTO);

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

           List <TransactionDTO> transactionsList =  transactionsServiceImpl.getTransactions(debtorDTO.getId());

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
        List<TransactionDTO> transactionsList = transactionsServiceImpl.getTransactions(idUser);
        //model.addAttribute("connectionsList", connectionsList);
        model.addAttribute("transactionsList", transactionsList);

        model.addAttribute("transactionForm",new TransactionForm());
        return "transferTest";
    }

    /*@PostMapping("/transactions/list")
    public String postlistTransactions(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        PageImpl transactionsPage = transactionsService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("transactionsPage", transactionsPage);

        int totalPages = transactionsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "transferTest";
    }*/

   /* @GetMapping("/displayConnectionsList")
    public String displayConnectionsList(Model model,@ModelAttribute("user") ConnectionDTO connectionDTO){
        List<ConnectionDTO> connectionsList = transactionService.getConnections(connectionDTO);

        model.addAttribute("connectionsList", connectionsList);

        return "transfer";
    }*/
   @GetMapping("/page/{pageNo}")
   public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
       int pageSize = 3;

       Page<Transactions> page = transactionsServiceImpl.findPaginated(pageNo, pageSize);
       List < Transactions > transactionsList = page.getContent();

       model.addAttribute("currentPage", pageNo);
       model.addAttribute("totalPages", page.getTotalPages());
       model.addAttribute("totalItems", page.getTotalElements());
       model.addAttribute("transactionsList", transactionsList);
       return "transferTest";
   }

    @GetMapping("/")
    /*public String home(Model model,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size){*/
    public String home (Model model){
        /*int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        Page<Book> transactionsPage = transactionsService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("transactionsPage", transactionsPage);

        int totalPages = transactionsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }*/

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());
        List<ConnectionDTO> allConnectionsList = connectionsService.getAllConnections();
        List<RelationsConnection> connectionsList = relationService.getRelations(connectionDTO);
        List<TransactionsConnection> transactionsList = transactionsServiceImpl.getTransactionsFromUser(connectionDTO);
        List<BankAccountDTO> bankAccountDTOList = bankAccountService.getUserAccountsList(connectionDTO);

        model.addAttribute("allConnectionsList", allConnectionsList);
        model.addAttribute("connectionsList", connectionsList);
        model.addAttribute("transactionsList", transactionsList);/**/
        model.addAttribute("username", connectionDTO.getName());
        model.addAttribute("debtorAccountList",bankAccountDTOList);

        //return "transferTest";
        return findPaginated(1, model);
    }


}
