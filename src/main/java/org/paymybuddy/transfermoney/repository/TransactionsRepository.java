package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionsRepository extends CrudRepository<TransactionEntity,Long> {


}
