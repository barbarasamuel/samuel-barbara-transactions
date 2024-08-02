package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.Mapper.RelationMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Relation;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.RelationDTO;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RelationService {
    @Autowired
    RelationRepository relationRepository;
    @Autowired
    RelationMapper relationMapper;
    @Autowired
    ConnectionMapper connectionMapper;

    @Autowired
    ConnectionsRepository connectionsRepository;
    public List<RelationDTO> getRelations(ConnectionDTO user){
        Connection connection = connectionMapper.convertToEntity(user);
        List<Relation> relationsList = relationRepository.findByUser(connection);
        return relationMapper.convertListToDTO(relationsList);
    }
}
