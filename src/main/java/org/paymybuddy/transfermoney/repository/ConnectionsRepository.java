package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.Connection;
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

    Optional<Connection> findById(Long id);

    List<Connection> findAllByOrderByEmailAsc();

}
