package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ConnectionDTO> getConnections(String username){
        //ConnectionsEntity connectionsEntity = connectionMapper.convertToEntity(connectionDTO);
        //List<ConnectionsEntity> connectionsEntityList = connectionsRepository.findAllNameByIdDifferentJPQL(connectionsEntity.getIdConnection());

        //List<ConnectionsEntity> connectionsEntityList = connectionsRepository.findAllNameByIdDifferentJPQL(1L);
        List<Connection> connectionList = connectionsRepository.findAllByEmailNot(username);
        return connectionMapper.convertListToDTO(connectionList);
    }

    public ConnectionDTO findByEmail(String email){
        Connection connection = connectionsRepository.findByEmail(email);
        return connectionMapper.convertToDTO(connection);
    }/**/

    public ConnectionDTO save(ConnectionDTO newConnectionDTO){


        Connection connection = connectionMapper.convertToEntity(newConnectionDTO);
        connection = connectionsRepository.save(connection);
        return connectionMapper.convertToDTO(connection);
    }
}
