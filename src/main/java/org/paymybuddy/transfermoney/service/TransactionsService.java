package org.paymybuddy.transfermoney.service;

import jakarta.transaction.Transactional;
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
@Transactional
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
        List<Connection> connectionList = connectionsRepository.findAllByEmailNot(username);
        return connectionMapper.convertListToDTO(connectionList);
    }

    public List<TransactionDTO> getTransactions(Long idCreditorAccount){
        List<Transactions> transactionsEntityList= transactionsRepository.findAllByIdCreditorIdConnection(1L);//(idCreditorAccount);
        return transactionMapper.convertListToDTO(transactionsEntityList);
    }

}
