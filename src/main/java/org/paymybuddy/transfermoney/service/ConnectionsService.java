package org.paymybuddy.transfermoney.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.entity.*;
import org.paymybuddy.transfermoney.mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class ConnectionsService {
    @Autowired
    ConnectionsRepository connectionsRepository;
    @Autowired
    ConnectionMapper connectionMapper;
    @Autowired
    RelationService relationService;
    @Autowired
    ContactService contactService;
    @Autowired
    TransactionsService transactionsService;
    @Autowired
    BankAccountService bankAccountService;
    @Transactional
    public List <TransactionDTO> saveTransaction(TransactionForm transactionForm){
        Connection creditor = getCreditor(transactionForm.getId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Connection debtor = getIdentifiant(userDetails.getUsername());

        /*TransactionDTO transactionDTO = TransactionDTO.builder()
                .creditor(creditorDTO)
                .debtor(debtorDTO)
                .description()
                .amount()
                .transactionDate()
                .build();*/
        Transactions transactions = new Transactions();
        transactions.setCreditor(creditor);
        transactions.setDebtor(debtor);
        transactions.setDescription(transactionForm.getDescription());
        transactions.setAmount(transactionForm.getAmount());
        transactions.setTransactionDate(new Date());

        Transactions newTransaction = transactionsService.saveTransaction(transactions);

        BankAccount debtorAccount = bankAccountService.getConnectionAccount(transactionForm.getIdDebtorAccount());
        Double updatedDebtorBalance = bankAccountService.updateDebtorAccount(debtorAccount,transactionForm.getAmount());

        debtorAccount.setId(transactionForm.getIdDebtorAccount());
        debtorAccount.setConnectionBankAccount(debtor);
        debtorAccount.setBalance(updatedDebtorBalance);
        bankAccountService.saveBankAccount(debtorAccount);

        BankAccount creditorAccount = bankAccountService.getConnectionAccount(transactionForm.getIdCreditorAccount());
        Double updatedCreditorBalance = bankAccountService.updateCreditorAccount(creditorAccount,transactionForm.getAmount());

        creditorAccount.setId(transactionForm.getIdCreditorAccount());
        creditorAccount.setConnectionBankAccount(creditor);
        creditorAccount.setBalance(updatedCreditorBalance);
        bankAccountService.saveBankAccount(creditorAccount);

        log.info("Transaction successful");

        return transactionsService.getTransactions(debtor.getId());
    }

    /**
     *
     * To manage the pagination of the transactions table
     *
     */
    public ManagePaginationDTO managePagination(int pageNo, String sortField, String sortDir){
        int pageSize = 3;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Connection connection = getIdentifiant(userDetails.getUsername());

        Page<Transactions> page = transactionsService.findPaginated(connection, pageNo, pageSize, sortField, sortDir);
        List < Transactions > transactionsList = page.getContent();

        List<ConnectionDTO> allConnectionsList = getAllConnections();
        List<RelationsConnection> connectionsList = relationService.getRelations(connection);
        List<BankAccountDTO> bankAccountDTOList = bankAccountService.getUserAccountsList(connection);

        ConnectionDTO connectionDTO = connectionMapper.convertToDTO(connection);

        return ManagePaginationDTO.builder()
                .allConnectionsList(allConnectionsList)
                .connectionsList(connectionsList)
                .connectionDTO(connectionDTO)
                .bankAccountDTOList(bankAccountDTOList)
                .page(page)
                .transactionsList(transactionsList)
                .build();
    }

    /**
     *
     * To load the transferTest.html page
     *
     */
    public TransferPageDTO accessTransferPage(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Connection connection = getIdentifiant(userDetails.getUsername());
        List<ConnectionDTO> allConnectionsList = getAllConnections();
        List<RelationsConnection> connectionsList = relationService.getRelations(connection);
        List<TransactionsConnection> transactionsList = transactionsService.getTransactionsFromUser(connection);
        List<BankAccountDTO> bankAccountDTOList = bankAccountService.getUserAccountsList(connection);

        ConnectionDTO connectionDTO = connectionMapper.convertToDTO(connection);
        return TransferPageDTO.builder()
                .connectionDTO(connectionDTO)
                .allConnectionsList(allConnectionsList)
                .connectionsList(connectionsList)
                .transactionsList(transactionsList)
                .bankAccountDTOList(bankAccountDTOList)
                .build();
    }

    /**
     *
     * To fill the dropdown content
     *
     */
    public List<BankAccountDTO> fillDropdown(Long selectedValue) {
        Connection connection = getCreditor(selectedValue);
        return bankAccountService.getUserAccountsList(connection);
    }

    /**
     *
     * To add a connection
     *
     */

    @Transactional
    public List<RelationsConnection> addConnection(String friendName){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Connection connection = getIdentifiant(userDetails.getUsername());
        Connection newConnection = getCreditor(Long.valueOf(friendName));

        Relation foundRelation = relationService.getRelation(newConnection, connection);

        if (foundRelation != null) {
            //error..rejectValue("name", null, "There is already a relation "+connectionDTO.getEmail() +" with that email");
            log.error("There is already a relation with "+ newConnection.getEmail());

        }else {
            /*RelationDTO relationDTO = RelationDTO.builder()
                    .user(DTO)
                    .connectionFriend(DTO)
                    .build();*/
            Relation relation = new Relation();
            relation.setUser(connection);
            relation.setConnectionFriend(newConnection);

            relationService.newRelation(relation);
            log.info("Created relation with "+ newConnection.getEmail());
        }

        return relationService.getRelations(connection);
    }

    /**
     *
     * To save a message
     *
     */
    public void addMessage(String message){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Connection connection = getIdentifiant(userDetails.getUsername());

        /*ContactDTO contactDTO = ContactDTO.builder()
                .sender(connectionDTO)
                .message(message)
                .build();*/

        Contact contact = new Contact();
        contact.setSender(connection);
        contact.setMessage(message);

        contactService.addedMessage(contact);

        log.info("New message");
    }

    /**
     *
     * To update the email
     *
     */
    public void emailUpdating(ProfileForm profileForm){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Connection connection = getIdentifiant(userDetails.getUsername());
        connection.setEmail(profileForm.getEmail());

        updatedConnection(connection);

        log.info("Modified email");
    }

    /**
     *
     * To update the password
     *
     */
    public ConnectionDTO passwordUpdatingStart(ProfileForm profileForm){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Connection connection = getIdentifiant(userDetails.getUsername());
        profileForm.setEmail(connection.getEmail());

        return connectionMapper.convertToDTO(connection);
    }

    public void passwordUpdatingFollowing(ConnectionDTO connectionDTO, ProfileForm profileForm){
        //connectionDTO.setPassword(profileForm.getConfirmPassword());
        Connection connection = connectionMapper.convertToEntity(connectionDTO);
        connection.setPassword(profileForm.getConfirmPassword());
        updatedConnection(connection);

        log.info("Modified password");
    }

    /**
     *
     * To get the profile
     *
     */
    public ProfileForm getProfile(ProfileForm profileForm){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Connection connection = getIdentifiant(userDetails.getUsername());

        profileForm.setEmail(connection.getEmail());
        profileForm.setOldPassword(connection.getPassword());

        return profileForm;
    }

    /**
     *
     * To check whether the email exists
     *
     */
    public ConnectionDTO checkEmail(String email){
        Connection connection = connectionsRepository.findByEmail(email);
        return connectionMapper.convertToDTO(connection);
    }

    /**
     *
     * To get all the connections
     *
     */
    public List<ConnectionDTO> getAllConnections(){
        List<Connection> connectionList = connectionsRepository.findAllByOrderByEmailAsc();
        return connectionMapper.convertListToDTO(connectionList);
    }

    /**
     *
     * To get a connection thanks to his/her email
     *
     */
    public Connection getIdentifiant(String email){
        return connectionsRepository.findByEmail(email);
        //return connectionMapper.convertToDTO(connection);
    }


    /**
     *
     * To get a connection thanks to his/her id
     *
     */
    public Connection getCreditor(Long id){
        Optional<Connection> connection = connectionsRepository.findById(id);
        return connection.get();
        //return connectionMapper.convertToDTO(connection.get());
    }

    /**
     *
     * To save a new connection
     *
     */
    public ConnectionDTO save(ConnectionDTO newConnectionDTO){

        Connection connection = connectionMapper.convertToEntity(newConnectionDTO);
        connection = connectionsRepository.save(connection);
        return connectionMapper.convertToDTO(connection);
    }

    /**
     *
     * To update a connection
     *
     */
    public void updatedConnection(Connection connection) {
        //Connection connection = connectionMapper.convertToEntity(connectionDTO);
        connectionsRepository.save(connection);
    }

}
