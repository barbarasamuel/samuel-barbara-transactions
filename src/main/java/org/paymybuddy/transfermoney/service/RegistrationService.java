package org.paymybuddy.transfermoney.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.Mapper.BankAccountMapper;
import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
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
        ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .email(registerForm.getEmail())
                .name(registerForm.getName())
                .password((registerForm.getPassword()))
                .build();

        ConnectionDTO newConnectionDTO = saveConnection(connectionDTO);

        saveBankAccount(newConnectionDTO);

        log.info("New user created");
    }

    /**
     *
     * To add a new user
     *
     */
    public ConnectionDTO saveConnection(ConnectionDTO connectionDTO){

        Connection connection = connectionMapper.convertToEntity(connectionDTO);
        connection = connectionsRepository.save(connection);

        return connectionMapper.convertToDTO(connection);

    }

    /**
     *
     * To create a bank account for a new user
     *
     */
    public void saveBankAccount(ConnectionDTO connectionDTO){

        Connection connection = connectionMapper.convertToEntity(connectionDTO);

        BankAccountDTO bankAccountDTO = BankAccountDTO.builder()
                .balance(50.00)
                .build();

        BankAccount bankAccount = bankAccountMapper.convertToEntity(bankAccountDTO);
        bankAccount.setConnectionBankAccount(connection);
        bankAccountRepository.save(bankAccount);
    }
}
