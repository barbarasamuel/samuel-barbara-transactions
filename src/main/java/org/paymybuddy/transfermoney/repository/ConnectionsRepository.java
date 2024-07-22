package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.Connection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectionsRepository extends CrudRepository<Connection,Long>{
    Connection findByEmail(String email);


    /*@Query(value="SELECT * FROM connection WHERE id_connection not in (:connections)", nativeQuery=true)
    List<Connection> findAllNameNotUsername(@Param("connections")String username);*/

    List<Connection> findAllByEmailNot(String email);

    /*@Query(value="SELECT id_connection, name FROM connection WHERE id_connection not in (:connections)", nativeQuery=true)
    public List<Connection> findAllNameNotIn(@Param("connections")Long idConnection);*/
    //public List<Connection> findAllNameNotUsername(@Param("connections")Long idConnection);
}
