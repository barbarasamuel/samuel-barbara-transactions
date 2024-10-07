package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.Mapper.RelationMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Relation;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RelationService {
    @Autowired
    RelationRepository relationRepository;
    @Autowired
    RelationMapper relationMapper;
    @Autowired
    ConnectionMapper connectionMapper;

    /**
     *
     * To get the list of friends
     *
     */
    public List<RelationsConnection> getRelations(ConnectionDTO user){

        List<RelationsConnection> relationsConnectionList = new ArrayList<>();
        List<Relation> relationsEntityList = relationRepository.findByUserIdOrderByConnectionFriendNameAsc(user.getId());
        List<RelationDTO> relationsDTOList= relationMapper.convertListToDTO(relationsEntityList);
        for(RelationDTO relationDTO:relationsDTOList){
            RelationsConnection relationsConnection = new RelationsConnection(
                    relationDTO.getConnectionFriend().getId(),
                    relationDTO.getConnectionFriend().getName()
            );
            relationsConnectionList.add(relationsConnection);
        }
        return relationsConnectionList;

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

    public RelationDTO getRelation(ConnectionDTO connectionFriendDTO, ConnectionDTO connectionUserDTO){
        Connection connectionFriend = connectionMapper.convertToEntity(connectionFriendDTO);
        Connection connectionUser = connectionMapper.convertToEntity(connectionUserDTO);
        Relation relation = relationRepository.findByConnectionFriendAndUser(connectionFriend, connectionUser);
        return relationMapper.convertToDTO(relation);
    }
}
