package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.Connection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectionsRepository extends CrudRepository<Connection,Long>{
    public Connection findByEmail(String email);
    @Query(value="SELECT id_connection, name FROM connection WHERE id_connection<>:id", nativeQuery=true)
    public List<Connection> findAllNameByIdDifferentJPQL(@Param("id")Long idConnection);

    @Query(value="SELECT id_connection, name FROM connection WHERE id_connection not in (:connections)", nativeQuery=true)
    public List<Connection> findAllNameNotUsername(@Param("connections")String username);

    /*@Query(value="SELECT id_connection, name FROM connection WHERE id_connection not in (:connections)", nativeQuery=true)
    public List<ConnectionsEntity> findAllNameNotUsername(@Param("connections")Long idConnection);*/
}
