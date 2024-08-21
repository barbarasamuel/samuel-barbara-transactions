package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.Mapper.RelationMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Relation;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     *
     * To get the list of friends
     *
     */
    public List<RelationsConnection> getRelations(ConnectionDTO user){

        List<RelationsConnection> relationsConnectionList = new ArrayList<>();
        //List<Relation> relationsEntityList = relationRepository.findByConnectionFriendId(user.getId());
        List<Relation> relationsEntityList = relationRepository.findByUserId(user.getId());
        List<RelationDTO> relationsDTOList= relationMapper.convertListToDTO(relationsEntityList);
        for(RelationDTO relationDTO:relationsDTOList){
            RelationsConnection relationsConnection = new RelationsConnection(
                    relationDTO.getConnectionFriend().getId(),
                    relationDTO.getConnectionFriend().getName()
            );
            relationsConnectionList.add(relationsConnection);
        }
        return relationsConnectionList;
        /*
        Connection connection = connectionMapper.convertToEntity(user);
        List<Relation> relationsList = relationRepository.findByUser(connection);
        return relationMapper.convertListToDTO(relationsList);*/
    }

    /**
     *
     * To create a new relation
     *
     */
    public void newRelation(RelationDTO relationDTO){
        Relation relation = relationMapper.convertToEntity(relationDTO);
        relationRepository.save(relation);
    }
}
