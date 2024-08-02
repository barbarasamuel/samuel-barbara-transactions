package org.paymybuddy.transfermoney;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.paymybuddy.transfermoney.entity.Connection;

public class RegistrationServiceTests {
    @Test
    @Transactional
    public void shouldUpdateExistingEntryInDBWithoutSave() {
        /*Connection connection = new Connection(
                ORIGINAL_TITLE, BigDecimal.ONE);
        connection = repository.save(connection);

        Long connectionId = connection.getId();

        // Update using setters
        pants.setTitle(UPDATED_TITLE);
        pants.setPrice(BigDecimal.TEN);
        pants.setBrand(UPDATED_BRAND);

        Optional<MerchandiseEntity> resultOp = repository.findById(connectionId);

        assertTrue(resultOp.isPresent());
        MerchandiseEntity result = resultOp.get();

        assertEquals(originalId, result.getId());
        assertEquals(UPDATED_TITLE, result.getTitle());
        assertEquals(BigDecimal.TEN, result.getPrice());
        assertEquals(UPDATED_BRAND, result.getBrand());*/
    }
}
