package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.Mapper.TransactionMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
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
ConnectionsRepository connectionsRepository;
@Autowired
ConnectionMapper connectionMapper;

    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        Transactions transactions = transactionMapper.convertToEntity(transactionDTO);
        transactions = transactionsRepository.save(transactions);
        return transactionMapper.convertToDTO(transactions);
    }

    //A mettre dans ConnectionService
    public List<ConnectionDTO> getConnections(String username){
        //ConnectionsEntity connectionsEntity = connectionMapper.convertToEntity(connectionDTO);
        //List<ConnectionsEntity> connectionsEntityList = connectionsRepository.findAllNameByIdDifferentJPQL(connectionsEntity.getIdConnection());

        //List<ConnectionsEntity> connectionsEntityList = connectionsRepository.findAllNameByIdDifferentJPQL(1L);
        List<Connection> connectionList = connectionsRepository.findAllNameNotUsername(username);
        return connectionMapper.convertListToDTO(connectionList);
    }

    public List<TransactionDTO> listTransactions(String username){
        List<Transactions> transactionsEntityList= transactionsRepository.findAllByUsername(username);
        return transactionMapper.convertListToDTO(transactionsEntityList);
    }

}
