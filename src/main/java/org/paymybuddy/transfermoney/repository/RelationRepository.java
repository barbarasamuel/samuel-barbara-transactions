package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Relation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RelationRepository extends CrudRepository<Relation,Long> {
    List<Relation> findByUser(Connection user);
    List<Relation> findByConnectionFriendId(Long user);
    List<Relation> findByUserId(Long user);
}
