package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.BankAccountEntity;
import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends CrudRepository<BankAccountEntity,Long>{
    public BankAccountEntity findByConnection(ConnectionsEntity connection);
}
