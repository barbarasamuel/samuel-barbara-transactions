package org.paymybuddy.transfermoney.integration;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.mapper.BankAccountMapper;
import org.paymybuddy.transfermoney.mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.mapper.ContactMapper;
import org.paymybuddy.transfermoney.mapper.TransactionMapper;
import org.paymybuddy.transfermoney.model.TransactionForm;
import org.paymybuddy.transfermoney.model.TransactionsConnection;
import org.paymybuddy.transfermoney.repository.BankAccountRepository;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.ContactRepository;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.paymybuddy.transfermoney.service.BankAccountService;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.ContactService;
import org.paymybuddy.transfermoney.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ConnectionsServiceIT {
    @InjectMocks
    private ConnectionsService connectionsService;
    @Mock
    private ConnectionsRepository connectionsRepository;
    @Mock
    private ConnectionMapper connectionMapper;
    @InjectMocks
    private ContactService contactService;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private ContactMapper contactMapper;

    @Autowired
    //@InjectMocks
    private BankAccountService bankAccountService;
    @Autowired//@Mock
    private BankAccountRepository bankAccountRepository;
    @Autowired//@Mock
    private BankAccountMapper bankAccountMapper;/**/

    @InjectMocks
    private TransactionsService transactionsService;
    @Mock
    private TransactionsRepository transactionsRepository;
    @Mock
    private TransactionMapper transactionMapper;/**/

    /**
     *
     * Should save a transaction
     *
     */
    @Test
    @Transactional
    public void shouldSaveTransactionIT() throws InstantiationException, IllegalAccessException {
        //Arrange
        Connection creditor = new Connection();
        creditor.setId(24L);
        creditor.setName("Geraldine");
        creditor.setEmail("geraldine@email.com");
        creditor.setPassword("Mo@depa2");

        Connection debtor = new Connection();
        debtor.setId(2L);
        debtor.setName("Gerard");
        debtor.setEmail("gerard@email.com");
        debtor.setPassword("Mo@depa2");

        TransactionForm transactionForm = new TransactionForm();
        transactionForm.setId(1L);
        transactionForm.setDescription("Restaurant");
        transactionForm.setAmount(12.00);
        transactionForm.setIdDebtorAccount(2L);
        transactionForm.setIdCreditorAccount(8L);

        Transactions transaction = new Transactions();
        transaction.setId(1L);
        transaction.setDescription(transactionForm.getDescription());
        transaction.setAmount(transactionForm.getAmount());
        transaction.setCreditor(creditor);
        transaction.setDebtor(debtor);
        transaction.setTransactionDate(new Date());

        BankAccount debtorAccount = new BankAccount();
        debtorAccount.setId(2L);
        debtorAccount.setBalance(53.00);
        debtorAccount.setConnectionBankAccount(debtor);

        BankAccount creditorAccount = new BankAccount();
        creditorAccount.setId(8L);
        creditorAccount.setBalance(62.00);
        creditorAccount.setConnectionBankAccount(creditor);

        List<TransactionsConnection> transactionsConnectionList = new ArrayList<>();
        TransactionsConnection transactionsConnection = new TransactionsConnection(
                transaction.getTransactionDate(),
                transaction.getCreditor().getName(),
                transaction.getDescription(),
                transaction.getAmount()
        );
        transactionsConnectionList.add(transactionsConnection);

        List<Transactions> transactionsEntityList = new ArrayList<>();
        transactionsEntityList.add(transaction);

        //getCreditor
        when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(creditor));
        //getIdentifiant
        when(connectionsRepository.findByEmail(any(String.class))).thenReturn(debtor);
        when(transactionsService.saveTransaction(any(Transactions.class))).thenReturn(transaction);

        //Act
        Transactions newTransactionResponse = transactionsService.saveTransaction(transaction);

        //Assert
        assertNotNull(newTransactionResponse);
        assertEquals(transaction.getTransactionDate(),newTransactionResponse.getTransactionDate());

    }

    /**
     *
     * Should  update debtor balance
     *
     */
    @Test
    @Transactional
    public void shouldSaveBankAccountIT() throws InstantiationException, IllegalAccessException {
        //Arrange
        Connection creditor = new Connection();
        creditor.setId(24L);
        creditor.setName("Geraldine");
        creditor.setEmail("geraldine@email.com");
        creditor.setPassword("Mo@depa2");

        Connection debtor = new Connection();
        debtor.setId(2L);
        debtor.setName("Gerard");
        debtor.setEmail("gerard@email.com");
        debtor.setPassword("Mo@depa2");

        TransactionForm transactionForm = new TransactionForm();
        transactionForm.setId(1L);
        transactionForm.setDescription("Restaurant");
        transactionForm.setAmount(12.00);
        transactionForm.setIdDebtorAccount(2L);
        transactionForm.setIdCreditorAccount(8L);

        Transactions transaction = new Transactions();
        transaction.setId(1L);
        transaction.setDescription(transactionForm.getDescription());
        transaction.setAmount(transactionForm.getAmount());
        transaction.setCreditor(creditor);
        transaction.setDebtor(debtor);
        transaction.setTransactionDate(new Date());

        BankAccount debtorAccount = new BankAccount();
        debtorAccount.setId(2L);
        debtorAccount.setBalance(53.00);
        debtorAccount.setConnectionBankAccount(debtor);

        BankAccount creditorAccount = new BankAccount();
        creditorAccount.setId(8L);
        creditorAccount.setBalance(62.00);
        creditorAccount.setConnectionBankAccount(creditor);

        List<TransactionsConnection> transactionsConnectionList = new ArrayList<>();
        TransactionsConnection transactionsConnection = new TransactionsConnection(
                transaction.getTransactionDate(),
                transaction.getCreditor().getName(),
                transaction.getDescription(),
                transaction.getAmount()
        );
        transactionsConnectionList.add(transactionsConnection);

        List<Transactions> transactionsEntityList = new ArrayList<>();
        transactionsEntityList.add(transaction);

        //getCreditor
        when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(creditor));
        //getIdentifiant
        when(connectionsRepository.findByEmail(any(String.class))).thenReturn(debtor);

        //Act
        bankAccountService.saveBankAccount(debtorAccount);

        //Assert
        verify(bankAccountRepository,times(1)).save(debtorAccount);
        assertNotNull(bankAccountRepository.save(debtorAccount));
        assertEquals(debtorAccount.getBalance(),bankAccountRepository.save(debtorAccount).getBalance());

    }

    /**
     *
     * Should  get the transactions from the debtor/user
     *
     */
    @Test
    public void shouldGetTransactionsIT() {
        //Arrange
        Connection creditor = new Connection();
        creditor.setId(24L);
        creditor.setName("Geraldine");
        creditor.setEmail("geraldine@email.com");
        creditor.setPassword("Mo@depa2");

        Connection debtor = new Connection();
        debtor.setId(2L);
        debtor.setName("Gerard");
        debtor.setEmail("gerard@email.com");
        debtor.setPassword("Mo@depa2");

        TransactionForm transactionForm = new TransactionForm();
        transactionForm.setId(1L);
        transactionForm.setDescription("Restaurant");
        transactionForm.setAmount(12.00);
        transactionForm.setIdDebtorAccount(2L);
        transactionForm.setIdCreditorAccount(8L);

        Transactions transaction = new Transactions();
        transaction.setId(1L);
        transaction.setDescription(transactionForm.getDescription());
        transaction.setAmount(transactionForm.getAmount());
        transaction.setCreditor(creditor);
        transaction.setDebtor(debtor);
        transaction.setTransactionDate(new Date());

        BankAccount debtorAccount = new BankAccount();
        debtorAccount.setId(2L);
        debtorAccount.setBalance(53.00);
        debtorAccount.setConnectionBankAccount(debtor);

        BankAccount creditorAccount = new BankAccount();
        creditorAccount.setId(8L);
        creditorAccount.setBalance(62.00);
        creditorAccount.setConnectionBankAccount(creditor);

        List<TransactionsConnection> transactionsConnectionList = new ArrayList<>();
        TransactionsConnection transactionsConnection = new TransactionsConnection(
                transaction.getTransactionDate(),
                transaction.getCreditor().getName(),
                transaction.getDescription(),
                transaction.getAmount()
        );
        transactionsConnectionList.add(transactionsConnection);

        List<Transactions> transactionsEntityList = new ArrayList<>();
        transactionsEntityList.add(transaction);

        //getCreditor
        when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(creditor));
        //getIdentifiant
        when(connectionsRepository.findByEmail(any(String.class))).thenReturn(debtor);

        //Act
        List<Transactions> transactionsResponseList =  transactionsService.getTransactions(debtor.getId());

        //Assert
        assertNotNull(transactionsResponseList);

    }

}
