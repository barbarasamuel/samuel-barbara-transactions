package org.paymybuddy.transfermoney.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.mapper.BankAccountMapper;
import org.paymybuddy.transfermoney.mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.model.BankAccountDTO;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.RegisterForm;
import org.paymybuddy.transfermoney.repository.BankAccountRepository;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class RegistrationService {

    @Autowired
    ConnectionMapper connectionMapper;
    @Autowired
    BankAccountMapper bankAccountMapper;
    @Autowired
    ConnectionsRepository connectionsRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;

    /**
     *
     * To save a registration
     *
     */
    @Transactional
    public void saveRegistration(RegisterForm registerForm){
        /*ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .email(registerForm.getEmail())
                .name(registerForm.getName())
                .password((registerForm.getPassword()))
                .build();*/
        Connection connection = new Connection();
        connection.setEmail(registerForm.getEmail());
        connection.setName(registerForm.getName());
        connection.setPassword(registerForm.getPassword());

        Connection newConnection = saveConnection(connection);

        saveBankAccount(newConnection);

        log.info("New user created");
    }

    /**
     *
     * To add a new user
     *
     */
    public Connection saveConnection(Connection connection){

        //Connection connection = connectionMapper.convertToEntity(connectionDTO);
        return connectionsRepository.save(connection);

        //return connectionMapper.convertToDTO(connection);

    }

    /**
     *
     * To create a bank account for a new user
     *
     */
    public void saveBankAccount(Connection connection){

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(50.00);

        bankAccount.setConnectionBankAccount(connection);
        bankAccountRepository.save(bankAccount);
    }
}
