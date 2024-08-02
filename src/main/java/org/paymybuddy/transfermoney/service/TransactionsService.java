package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.Mapper.TransactionMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionsService {
    @Autowired
    TransactionsRepository transactionsRepository;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    ConnectionMapper connectionMapper;

    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        Transactions transactions = transactionMapper.convertToEntity(transactionDTO);
        transactions = transactionsRepository.save(transactions);
        return transactionMapper.convertToDTO(transactions);
    }

    public List<TransactionDTO> getTransactions(Long idDebtorAccount){
        List<Transactions> transactionsEntityList= transactionsRepository.findAllByDebtorId(idDebtorAccount);
        return transactionMapper.convertListToDTO(transactionsEntityList);
    }

    public List<TransactionDTO> getTransactionsFromUser(ConnectionDTO user){

        Connection connection = connectionMapper.convertToEntity(user);
        List<Transactions> transactionsList = transactionsRepository.findByDebtor(connection);

        return transactionMapper.convertListToDTO(transactionsList);
    }

}
