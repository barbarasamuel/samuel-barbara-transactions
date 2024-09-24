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


    /**
     *
     * To get all the connections
     *
     */
    public List<ConnectionDTO> getAllConnections(){
        List<Connection> connectionList = connectionsRepository.findAll();
        return connectionMapper.convertListToDTO(connectionList);
    }

    /**
     *
     * To get a connection thanks to his/her email
     *
     */
    public ConnectionDTO getIdentifiant(String email){
        Connection connection = connectionsRepository.findByEmail(email);
        return connectionMapper.convertToDTO(connection);
    }


    /**
     *
     * To get a connection thanks to his/her id
     *
     */
    public ConnectionDTO getCreditor(Long id){
        Optional<Connection> connection = connectionsRepository.findById(id);
        return connectionMapper.convertToDTO(connection.get());
    }

    /**
     *
     * To save a new connection
     *
     */
    public ConnectionDTO save(ConnectionDTO newConnectionDTO){

        Connection connection = connectionMapper.convertToEntity(newConnectionDTO);
        connection = connectionsRepository.save(connection);
        return connectionMapper.convertToDTO(connection);
    }

    /**
     *
     * To update a connection
     *
     */
    public void updatedConnection(ConnectionDTO connectionDTO) {
        Connection connection = connectionMapper.convertToEntity(connectionDTO);
        connectionsRepository.save(connection);
    }

}
