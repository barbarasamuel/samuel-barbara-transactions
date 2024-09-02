package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * To get the information from the database linked to the bankAccount table
 *
 */
public interface BankAccountRepository extends CrudRepository<BankAccount,Long>{
    public BankAccount findByConnectionBankAccountId(Long connection);
    public List<BankAccount> findAllByConnectionBankAccountId(Long connection);
}
