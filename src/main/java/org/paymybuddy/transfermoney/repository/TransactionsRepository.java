package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * To get the information from the database linked to the transactions table
 *
 */
public interface TransactionsRepository extends CrudRepository<Transactions,Long> {

    List<Transactions> findByDebtorId(Long idDebtorAccount);

    List<Transactions> findByDebtor(Connection debtor);
    Page<Transactions> findAllByDebtor(Connection connection, Pageable pageable);
}
