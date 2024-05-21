package org.paymybuddy.transfermoney.service.implementation;

import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class ConnectionServiceImpl implements ConnectionsRepository{
    @Autowired
    ConnectionsRepository connectionsRepository;
    @Autowired
    ConnectionsEntity connectionsEntity;

    @Override
    public ConnectionsEntity findByEmail(String email) {
        return connectionsRepository.findByEmail(email);
    }
    @Override
    public void saveConnection(ConnectionDTO connectionDTO) {


    }



}
