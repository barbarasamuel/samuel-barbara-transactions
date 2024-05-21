package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.TransactionsEntity;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.springframework.data.repository.CrudRepository;

public interface TransactionsRepository extends CrudRepository<TransactionsEntity,Long> {

    public void saveTransaction(TransactionDTO transactionDTO);
}
