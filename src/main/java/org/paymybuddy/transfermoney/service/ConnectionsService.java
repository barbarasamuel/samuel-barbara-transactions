package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConnectionsService {
    @Autowired
    ConnectionsRepository connectionsRepository;
    @Autowired
    ConnectionMapper connectionMapper;
    public ConnectionDTO newConnection(ConnectionDTO connectionDTO) {

        Connection connection = connectionMapper.convertToEntity(connectionDTO);
        connection = connectionsRepository.save(connection);
        return connectionMapper.convertToDTO(connection);

    }

    public ConnectionDTO getIdentifiant(String email){
        Connection connection = connectionsRepository.findByEmail(email);
        return connectionMapper.convertToDTO(connection);
    }

    public ConnectionDTO getConnection(String name){
        Connection connection = connectionsRepository.findByName(name);
        return connectionMapper.convertToDTO(connection);
    }

    public ConnectionDTO save(ConnectionDTO newConnectionDTO){


        Connection connection = connectionMapper.convertToEntity(newConnectionDTO);
        connection = connectionsRepository.save(connection);
        return connectionMapper.convertToDTO(connection);
    }

    public void updatedConnection(ConnectionDTO connectionDTO) {
        Connection connection = connectionMapper.convertToEntity(connectionDTO);
        connectionsRepository.save(connection);
    }

}
