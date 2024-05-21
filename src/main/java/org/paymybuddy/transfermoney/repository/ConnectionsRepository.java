package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionsRepository extends CrudRepository<ConnectionsEntity,Long>{
    public ConnectionsEntity findByEmail(String email);
    public void saveConnection(ConnectionDTO connectionDTO);
}
