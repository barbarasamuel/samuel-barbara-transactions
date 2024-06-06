package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ConnectionsRepository extends CrudRepository<ConnectionsEntity,Long>{
    public ConnectionsEntity findByEmail(String email);
    @Query(value="SELECT id_connection, name FROM connection WHERE id_connection<>:id", nativeQuery=true)
    public List<ConnectionsEntity> findAllNameByIdDifferentJPQL(@Param("id")Long idConnection);

}
