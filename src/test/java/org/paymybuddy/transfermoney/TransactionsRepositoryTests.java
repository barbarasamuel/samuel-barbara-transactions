package org.paymybuddy.transfermoney;

import org.junit.jupiter.api.Test;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionsRepositoryTests {
    @Autowired
    private TransactionsRepository transactionsRepository;

    @Test
    public void testAddTransaction(){

        Connection creditor = new Connection();
        Connection debtor = new Connection();

        Date transactionDate = new Date("2024-07-22 17:51:04.789463");
        Transactions expectedTransaction = new Transactions("testTransaction",10.05D,transactionDate, creditor, debtor);

        transactionsRepository.save(expectedTransaction);
        Optional<Transactions> getTransaction = transactionsRepository.findById(expectedTransaction.getId());
        assertEquals(expectedTransaction, getTransaction.orElseThrow());
    }

    @Test
    public void testGetTransactions(){
        List<Transactions> transactions = transactionsRepository.findByDebtorId(1L);

        assertNotNull(transactions,"The object transactions shouldn't be null.");
    }
}
