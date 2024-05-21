package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionsService {
    @Autowired
    private ConnectionsRepository connectionsRepository;
    @Autowired
    ConnectionsEntity connectionsEntity;

    public String newConnection(ConnectionDTO connectionDTO) {

        ConnectionsEntity existing = connectionsRepository.findByEmail(connectionDTO.getEmail());
        if (existing != null) {
            return "There is already an account registered with that email";
        }

        connectionsEntity.setName(connectionDTO.getName());
        connectionsEntity.setEmail(connectionDTO.getEmail());

        //encrypt the password once we integrate spring security
        connectionsEntity.setPassword(connectionDTO.getPassword());
        //connectionsEntity.setPassword(passwordEncoder.encode(connectionDTO.getPassword()));

        connectionsRepository.save(connectionsEntity);
        return "redirect:/register?success";

    }

    public String saveNewConnection(ConnectionDTO connection){

        connectionsEntity.setName(connection.getName());
        connectionsEntity.setIdConnection(connection.getIdConnection());

        connectionsRepository.save(connectionsEntity);
        return "redirect:/transfer?success";
    }/**/
}
