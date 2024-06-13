package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.Mapper.TransactionMapper;
import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.entity.TransactionEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
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
        TransactionEntity transaction = transactionMapper.convertToEntity(transactionDTO);
        transaction = transactionsRepository.save(transaction);
        return transactionMapper.convertToDTO(transaction);
    }

    //A mettre dans ConnectionService
    public List<ConnectionDTO> getConnections(String username){
        //ConnectionsEntity connectionsEntity = connectionMapper.convertToEntity(connectionDTO);
        //List<ConnectionsEntity> connectionsEntityList = connectionsRepository.findAllNameByIdDifferentJPQL(connectionsEntity.getIdConnection());

        //List<ConnectionsEntity> connectionsEntityList = connectionsRepository.findAllNameByIdDifferentJPQL(1L);
        List<ConnectionsEntity> connectionsEntityList = connectionsRepository.findAllNameNotUsername(username);
        return connectionMapper.convertListToDTO(connectionsEntityList);
    }

    public List<TransactionDTO> listTransactions(String username){
        List<TransactionEntity> transactionsEntityList= transactionsRepository.findAllByUsername(username);
        return transactionMapper.convertListToDTO(transactionsEntityList);
    }

}
