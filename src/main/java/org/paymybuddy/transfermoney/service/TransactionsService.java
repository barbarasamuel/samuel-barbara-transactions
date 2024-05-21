package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.entity.TransactionsEntity;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TransactionsService {
    @Autowired
    TransactionsRepository transactionRepository;

    @Autowired
    TransactionsEntity transactionsEntity;

    public String saveTransaction(TransactionDTO transactionDTO){

        transactionsEntity.setIdConnectionsEntityDebit(transactionDTO.getIdConnectionDebit());
        transactionsEntity.setIdConnectionsEntityCredit(transactionDTO.getIdConnectionCredit());
        transactionsEntity.setTypeTransaction("Debit");
        transactionsEntity.setDescription(transactionDTO.getDescription());
        transactionsEntity.setAmount(transactionDTO.getAmount());
        transactionsEntity.setTransactionDate(new Date());

        transactionRepository.save(transactionsEntity);
        return "transfer";
    }
}
