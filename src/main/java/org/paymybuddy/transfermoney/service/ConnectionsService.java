package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.Mapper.TransactionMapper;
import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.entity.TransactionEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.RegisterForm;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionsService {
    @Autowired
    ConnectionsRepository connectionsRepository;
    @Autowired
    ConnectionMapper connectionMapper;
    public ConnectionDTO newConnection(ConnectionDTO connectionDTO) {

        ConnectionsEntity connectionsEntity = connectionMapper.convertToEntity(connectionDTO);
        connectionsEntity = connectionsRepository.save(connectionsEntity);
        return connectionMapper.convertToDTO(connectionsEntity);
    }

    public ConnectionDTO findByEmail(String email){
        ConnectionsEntity connectionsEntity = connectionsRepository.findByEmail(email);
        return connectionMapper.convertToDTO(connectionsEntity);
    }/**/

    public ConnectionDTO save(ConnectionDTO newConnectionDTO){
        ConnectionsEntity connectionsEntity = connectionMapper.convertToEntity(newConnectionDTO);
        connectionsEntity = connectionsRepository.save(connectionsEntity);
        return connectionMapper.convertToDTO(connectionsEntity);
    }
}
