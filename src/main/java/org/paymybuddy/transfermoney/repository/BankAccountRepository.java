package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends CrudRepository<BankAccount,Long>{
    public BankAccount findByConnectionBankAccountId(Long connection);
}
