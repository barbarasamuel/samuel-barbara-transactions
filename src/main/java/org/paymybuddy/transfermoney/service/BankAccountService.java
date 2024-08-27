package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.BankAccountMapper;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.model.BankAccountDTO;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    BankAccountMapper bankAccountMapper;

    @Autowired
    BankAccountRepository bankAccountRepository;

    /**
     *
     * To get the connection bank account
     *
     */
    public BankAccountDTO getConnectionAccount(ConnectionDTO connectionDTO){

        BankAccount bankAccount = bankAccountRepository.findByConnectionBankAccountId(connectionDTO.getId());
        return bankAccountMapper.convertToDTO(bankAccount);

    }

    /**
     *
     * To calculate the new debtor balance
     *
     */
    public Double updateDebtorAccount(BankAccountDTO debtorAccountDTO, Double amount) {

        Double balanceWithTransaction = debtorAccountDTO.getBalance() - amount;
        Double finalBalance = balanceWithTransaction - (amount*0.5/100);
        finalBalance = Math.round(finalBalance * Math.pow(10,2)) / Math.pow(10,2);

        return finalBalance;
    }

    /**
     *
     * To calculate the new creditor balance
     *
     */
    public Double updateCreditorAccount(BankAccountDTO creditorAccountDTO,Double amount){

        Double finalBalance = creditorAccountDTO.getBalance() + amount;
        finalBalance = Math.round(finalBalance * Math.pow(10,2)) / Math.pow(10,2);

        return finalBalance;
    }

    /**
     *
     * To save or update the new balance
     *
     */
    public void saveBankAccount(BankAccountDTO bankAccountDTO){

        BankAccount bankAccount = bankAccountMapper.convertToEntity(bankAccountDTO);
        bankAccountRepository.save(bankAccount);
    }

    /**
     *
     * To update the new balance
     *
     */
    /*public void updateBankAccount(BankAccountDTO bankAccountDTO){

        BankAccount bankAccount = bankAccountMapper.convertToEntity(bankAccountDTO);
        bankAccountRepository.save(bankAccount);
    }*/
}
