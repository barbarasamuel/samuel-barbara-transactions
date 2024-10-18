package org.paymybuddy.transfermoney.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
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
        ConnectionDTO creditorDTO = getCreditor(transactionForm.getId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ConnectionDTO debtorDTO = getIdentifiant(userDetails.getUsername());

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

        log.info("Transaction successful");

        return transactionsService.getTransactions(debtorDTO.getId());
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
        ConnectionDTO connectionDTO = getIdentifiant(userDetails.getUsername());

        Page<Transactions> page = transactionsService.findPaginated(connectionDTO, pageNo, pageSize, sortField, sortDir);
        List < Transactions > transactionsList = page.getContent();

        List<ConnectionDTO> allConnectionsList = getAllConnections();
        List<RelationsConnection> connectionsList = relationService.getRelations(connectionDTO);
        List<BankAccountDTO> bankAccountDTOList = bankAccountService.getUserAccountsList(connectionDTO);

        ManagePaginationDTO managePaginationDTO = ManagePaginationDTO.builder()
                .allConnectionsList(allConnectionsList)
                .connectionsList(connectionsList)
                .connectionDTO(connectionDTO)
                .bankAccountDTOList(bankAccountDTOList)
                .page(page)
                .transactionsList(transactionsList)
                .build();

        return managePaginationDTO;
    }

    /**
     *
     * To load the transferTest.html page
     *
     */
    public TransferPageDTO accessTransferPage(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ConnectionDTO connectionDTO = getIdentifiant(userDetails.getUsername());
        List<ConnectionDTO> allConnectionsList = getAllConnections();
        List<RelationsConnection> connectionsList = relationService.getRelations(connectionDTO);
        List<TransactionsConnection> transactionsList = transactionsService.getTransactionsFromUser(connectionDTO);
        List<BankAccountDTO> bankAccountDTOList = bankAccountService.getUserAccountsList(connectionDTO);

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
        ConnectionDTO connectionDTO = getCreditor(selectedValue);
        return bankAccountService.getUserAccountsList(connectionDTO);
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

        ConnectionDTO connectionDTO = getIdentifiant(userDetails.getUsername());
        ConnectionDTO newConnectionDTO = getCreditor(Long.valueOf(friendName));

        RelationDTO foundRelationDTO = relationService.getRelation(newConnectionDTO, connectionDTO);

        if (foundRelationDTO != null) {
            //error..rejectValue("name", null, "There is already a relation "+connectionDTO.getEmail() +" with that email");
            log.error("There is already a relation with "+ newConnectionDTO.getEmail());

        }else {
            RelationDTO relationDTO = RelationDTO.builder()
                    .user(connectionDTO)
                    .connectionFriend(newConnectionDTO)
                    .build();

            relationService.newRelation(relationDTO);
            log.info("Created relation with "+ newConnectionDTO.getEmail());
        }

        return relationService.getRelations(connectionDTO);
    }

    /**
     *
     * To save a message
     *
     */
    public void addMessage(String message){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ConnectionDTO connectionDTO = getIdentifiant(userDetails.getUsername());

        ContactDTO contactDTO = ContactDTO.builder()
                .sender(connectionDTO)
                .message(message)
                .build();

        contactService.addedMessage(contactDTO);

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

        ConnectionDTO connectionDTO = getIdentifiant(userDetails.getUsername());
        connectionDTO.setEmail(profileForm.getEmail());

        updatedConnection(connectionDTO);

        log.info("Modified email");
    }

    /**
     *
     * To update the password
     *
     */
    public ConnectionDTO passwordUpdatingStart(ProfileForm profileForm, BindingResult bindingResult){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ConnectionDTO connectionDTO = getIdentifiant(userDetails.getUsername());
        profileForm.setEmail(connectionDTO.getEmail());

        return connectionDTO;
    }

    public void passwordUpdatingFollowing(ConnectionDTO connectionDTO, ProfileForm profileForm){
        connectionDTO.setPassword(profileForm.getConfirmPassword());

        updatedConnection(connectionDTO);

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

        ConnectionDTO connectionDTO = getIdentifiant(userDetails.getUsername());

        profileForm.setEmail(connectionDTO.getEmail());
        profileForm.setOldPassword(connectionDTO.getPassword());

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
    public ConnectionDTO getIdentifiant(String email){
        Connection connection = connectionsRepository.findByEmail(email);
        return connectionMapper.convertToDTO(connection);
    }


    /**
     *
     * To get a connection thanks to his/her id
     *
     */
    public ConnectionDTO getCreditor(Long id){
        Optional<Connection> connection = connectionsRepository.findById(id);
        return connectionMapper.convertToDTO(connection.get());
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
    public void updatedConnection(ConnectionDTO connectionDTO) {
        Connection connection = connectionMapper.convertToEntity(connectionDTO);
        connectionsRepository.save(connection);
    }

}
