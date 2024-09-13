package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.TransactionsConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * To get the information from the database linked to the transactions table
 *
 */
public interface TransactionsRepository extends CrudRepository<Transactions,Long> {

    /*@Query(value="SELECT description, amount FROM transactions inner join relation on  transactions.id_creditor_account = relation.id_user inner join connection on relation.id_connection = connection.id_connection WHERE name<>:username", nativeQuery=true)
    List<Transactions> findAllByUsername(@Param("username") String username);*/
    List<Transactions> findByDebtorId(Long idDebtorAccount);

    List<Transactions> findByDebtor(Connection debtor);
    Page<Transactions> findAll(Pageable pageable);
}
