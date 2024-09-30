package org.paymybuddy.transfermoney.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ConnectionDTO saveTransaction(TransactionForm transactionForm){
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

        return debtorDTO;
    }

    /**
     *
     * To add a connection
     *
     */

    @Transactional
    public ConnectionDTO addConnection(String friendName){

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

        return connectionDTO;
    }

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
     * To get all the connections
     *
     */
    public List<ConnectionDTO> getAllConnections(){
        List<Connection> connectionList = connectionsRepository.findAll();
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
