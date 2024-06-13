package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.entity.TransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionsRepository extends CrudRepository<TransactionEntity,Long> {

    @Query(value="SELECT description, amount FROM transactions inner join relation on  transactions.id_creditor_account = relation.id_user inner join connection on relation.id_connection = connection.id_connection WHERE name<>:username", nativeQuery=true)
    public List<TransactionEntity> findAllByUsername(@Param("username") String username);
}
