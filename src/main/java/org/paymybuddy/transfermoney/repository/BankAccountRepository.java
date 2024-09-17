package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.BankAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * To get the information from the database linked to the bankAccount table
 *
 */
public interface BankAccountRepository extends CrudRepository<BankAccount,Long>{
    public BankAccount findByConnectionBankAccountId(Long connection);
    public Optional<BankAccount> findById(Long id);
    public List<BankAccount> findAllByConnectionBankAccountId(Long connection);
}
