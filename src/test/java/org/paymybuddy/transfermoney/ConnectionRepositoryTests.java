package org.paymybuddy.transfermoney;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ConnectionRepositoryTests {
    @Autowired
    private ConnectionsRepository connectionsRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testAddConnection() {
        Connection connection = new Connection();
        connection.setEmail("jeanne@email.com");
        connection.setPassword("motdepa0");
        connection.setName("Jeanne");

        Connection savedConnection = connectionsRepository.save(connection);

        Connection existConnection = entityManager.find(Connection.class, savedConnection.getId());

        assertEquals(connection.getEmail(),existConnection.getEmail());

    }

}
