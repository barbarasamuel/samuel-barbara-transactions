package org.paymybuddy.transfermoney.service.implementation;


import org.paymybuddy.transfermoney.entity.TransactionsEntity;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TransactionServiceImpl {

    @Autowired
    TransactionsRepository transactionsRepository;
    @Autowired
    TransactionsEntity transactionsEntity;
/*
    @Override
    public void saveTransaction(TransactionDTO transactionDTO) {

        transactionsEntity.setConnectionsEntityDebit(transactionDTO.getName());
        transactionsEntity.setConnectionsEntityCredit(transactionDTO.getConnection());
        transactionsEntity.setTypeTransaction("Debit");
        transactionsEntity.setDescription(transactionDTO.getDescription());
        transactionsEntity.setAmount(transactionDTO.getAmount());

        transactionsRepository.save(transactionsEntity);
    }*/


}
