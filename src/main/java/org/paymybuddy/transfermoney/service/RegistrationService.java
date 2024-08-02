package org.paymybuddy.transfermoney.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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

    public ConnectionDTO saveConnection(ConnectionDTO connectionDTO){

        Connection connection = connectionMapper.convertToEntity(connectionDTO);
        connection = connectionsRepository.save(connection);

        return connectionMapper.convertToDTO(connection);

    }

    //public void saveBankAccount(BankAccountDTO bankAccountDTO){
    public void saveBankAccount(ConnectionDTO connectionDTO){

        Connection connection = connectionMapper.convertToEntity(connectionDTO);

        BankAccountDTO bankAccountDTO = BankAccountDTO.builder()
                .balance(50.00)
                //.connectionBankAccount(connectionDTO)
                .build();

        BankAccount bankAccount = bankAccountMapper.convertToEntity(bankAccountDTO);
        bankAccount.setConnectionBankAccount(connection);
        bankAccountRepository.save(bankAccount);
    }
}
