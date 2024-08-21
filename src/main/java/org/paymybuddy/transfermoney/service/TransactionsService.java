package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.Mapper.TransactionMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.model.TransactionsConnection;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionsService {
    @Autowired
    TransactionsRepository transactionsRepository;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    ConnectionMapper connectionMapper;

    /**
     *
     * To save a new transaction
     *
     */
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        Transactions transactions = transactionMapper.convertToEntity(transactionDTO);
        transactions = transactionsRepository.save(transactions);
        return transactionMapper.convertToDTO(transactions);
    }

    /**
     *
     * To get the list of transactions from the user
     *
     */
    public List<TransactionDTO> getTransactions(Long idDebtorAccount){
        List<TransactionsConnection> transactionsConnectionList = new ArrayList<>();
        List<Transactions> transactionsEntityList = transactionsRepository.findByDebtorId(idDebtorAccount);
        List<TransactionDTO> transactionsDTOList= transactionMapper.convertListToDTO(transactionsEntityList);
        for(TransactionDTO transactionDTO:transactionsDTOList){
            TransactionsConnection transactionsConnection = new TransactionsConnection(
                    transactionDTO.getTransactionDate(),
                    transactionDTO.getCreditor().getName(),
                    transactionDTO.getDescription(),
                    transactionDTO.getAmount()
            );
            transactionsConnectionList.add(transactionsConnection);
        }
        return transactionMapper.convertListToDTO(transactionsEntityList);
    }

    public List<TransactionsConnection> getTransactionsFromUser(ConnectionDTO user){

        List<TransactionsConnection> transactionsConnectionList = new ArrayList<>();
        List<Transactions> transactionsEntityList = transactionsRepository.findByDebtorId(user.getId());
        List<TransactionDTO> transactionsDTOList= transactionMapper.convertListToDTO(transactionsEntityList);
        for(TransactionDTO transactionDTO:transactionsDTOList){
            TransactionsConnection transactionsConnection = new TransactionsConnection(
                    transactionDTO.getTransactionDate(),
                    transactionDTO.getCreditor().getName(),
                    transactionDTO.getDescription(),
                    transactionDTO.getAmount()
            );
            transactionsConnectionList.add(transactionsConnection);
        }
        return transactionsConnectionList;

    }

}
