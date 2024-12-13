package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    /*@Autowired
    BankAccountMapper bankAccountMapper;*/

    @Autowired
    BankAccountRepository bankAccountRepository;

    /**
     *
     * To get the connection bank account
     *
     */
    public BankAccount getConnectionAccount(Long id){

        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(id);

        return (BankAccount) bankAccountOptional.get();

    }

    /**
     *
     * To calculate the new debtor balance
     *
     */
    public Double updateDebtorAccount(BankAccount debtorAccount, Double amount) {

        Double balanceWithTransaction = debtorAccount.getBalance() - amount;
        Double finalBalance = balanceWithTransaction - (amount*0.5/100);
        finalBalance = Math.round(finalBalance * Math.pow(10,2)) / Math.pow(10,2);

        return finalBalance;
    }

    /**
     *
     * To calculate the new creditor balance
     *
     */
    public Double updateCreditorAccount(BankAccount creditorAccount,Double amount){

        Double finalBalance = creditorAccount.getBalance() + amount;
        finalBalance = Math.round(finalBalance * Math.pow(10,2)) / Math.pow(10,2);

        return finalBalance;
    }

    /**
     *
     * To save or update the new balance
     *
     */
    public void saveBankAccount(BankAccount bankAccount){

        bankAccountRepository.save(bankAccount);
    }

    /**
     *
     * To get the user accounts list
     *
     */
    public List<BankAccount> getUserAccountsList(Connection connection){

        return bankAccountRepository.findAllByConnectionBankAccountId(connection.getId());

    }


}
