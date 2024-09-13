package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * To get the information from the database linked to the connection table
 *
 */
public interface ConnectionsRepository extends CrudRepository<Connection,Long>{
    Connection findByEmail(String email);

    Connection findByName(String name);
    /*@Query(value="SELECT * FROM connection WHERE id_connection not in (:connections)", nativeQuery=true)
    List<Connection> findAllNameNotUsername(@Param("connections")String username);*/
    Optional<Connection> findById(Long id);
    //Connection findById(Long id);
    List<Connection> findAllByEmailNot(String email);

    List<Connection> findAll();
    /*@Query(value="SELECT id_connection, name FROM connection WHERE id_connection not in (:connections)", nativeQuery=true)
    public List<Connection> findAllNameNotIn(@Param("connections")Long idConnection);*/
    //public List<Connection> findAllNameNotUsername(@Param("connections")Long idConnection);
}
