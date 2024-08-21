package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.Connection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConnectionsRepository extends CrudRepository<Connection,Long>{
    Connection findByEmail(String email);

    Connection findByName(String name);
    /*@Query(value="SELECT * FROM connection WHERE id_connection not in (:connections)", nativeQuery=true)
    List<Connection> findAllNameNotUsername(@Param("connections")String username);*/
    Optional<Connection> findById(Long id);
    //Connection findById(Long id);
    List<Connection> findAllByEmailNot(String email);

    /*@Query(value="SELECT id_connection, name FROM connection WHERE id_connection not in (:connections)", nativeQuery=true)
    public List<Connection> findAllNameNotIn(@Param("connections")Long idConnection);*/
    //public List<Connection> findAllNameNotUsername(@Param("connections")Long idConnection);
}
