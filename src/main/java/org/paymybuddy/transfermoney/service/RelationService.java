package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Relation;
import org.paymybuddy.transfermoney.model.RelationsConnection;
import org.paymybuddy.transfermoney.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationService {
    @Autowired
    RelationRepository relationRepository;
    /*@Autowired
    RelationMapper relationMapper;
    @Autowired
    ConnectionMapper connectionMapper;*/

    /**
     *
     * To get the list of friends
     *
     */
    public List<RelationsConnection> getRelations(Connection user){

        List<RelationsConnection> relationsConnectionList = new ArrayList<>();
        List<Relation> relationsEntityList = relationRepository.findByUserIdOrderByConnectionFriendNameAsc(user.getId());

        for(Relation relation:relationsEntityList){
            RelationsConnection relationsConnection = new RelationsConnection(
                    relation.getConnectionFriend().getId(),
                    relation.getConnectionFriend().getName()
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
    public void newRelation(Relation relation){

        relationRepository.save(relation);
    }

    public Relation getRelation(Connection connectionFriend, Connection connectionUser){

        return relationRepository.findByConnectionFriendAndUser(connectionFriend, connectionUser);

    }
}
