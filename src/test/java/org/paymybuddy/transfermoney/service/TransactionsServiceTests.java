package org.paymybuddy.transfermoney.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.TransactionsConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TransactionsServiceTests {
    @Autowired
    private TransactionsService transactionsService;

    /**
     * Should return 13 elements in the transactions list
     */
    @Test
    public void shouldReturnElementsInTheTransactionsList() {
        //Arrange
        Connection user = new Connection();
        user.setId(2L);
        user.setName("Gerard");
        user.setEmail("gerard@email.fr");
        user.setPassword("Mo@depa2");

        //Act
        List<TransactionsConnection> transactionsConnectionList = transactionsService.getTransactionsFromUser(user);

        //Assert
        assertEquals(13, transactionsConnectionList.size());
    }

    /**
     * Should return a part of the debtor transactions
     */
    @Test
    public void shouldReturnAPartOfTheDebtorTransactions() {
        //Arrange
        Connection debtor = new Connection();
        debtor.setId(2L);
        debtor.setName("Gerard");
        debtor.setEmail("gerard@email.fr");
        debtor.setPassword("Mo@depa2");

        int pageNo = 2;
        int pageSize = 3;
        String sortField = "transactionDate";
        String sortDirection = "DESC";

        //Act
        Page<Transactions> transactionsPage = transactionsService.findPaginated (debtor,pageNo, pageSize, sortField, sortDirection);

        //Assert
        assertEquals(pageSize,transactionsPage.getSize());
        //assertEquals(sortField+sortDirection,transactionsPage.getSort());
        assertEquals(pageNo-1,transactionsPage.getNumber());
    }
}
