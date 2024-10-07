package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Relation;
import org.paymybuddy.transfermoney.model.RelationDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * To get the information from the database linked to the relation table
 *
 */
public interface RelationRepository extends CrudRepository<Relation,Long> {
    List<Relation> findByUser(Connection user);
    List<Relation> findByConnectionFriendId(Long user);
    List<Relation> findByUserIdOrderByConnectionFriendNameAsc(Long user);
    Relation findByConnectionFriendAndUser(Connection connectionFriend, Connection user);
}
