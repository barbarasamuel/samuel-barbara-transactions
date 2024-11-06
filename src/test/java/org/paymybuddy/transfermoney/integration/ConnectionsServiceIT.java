package org.paymybuddy.transfermoney.integration;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.paymybuddy.transfermoney.mapper.BankAccountMapper;
import org.paymybuddy.transfermoney.mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.mapper.ContactMapper;
import org.paymybuddy.transfermoney.mapper.TransactionMapper;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.*;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

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
        /*ConnectionDTO creditorDTO = ConnectionDTO.builder()
                .id(24L)
                .name("Geraldine")
                .email("geraldined@email.com")
                .password("Mo@depa2")
                .build();*/

        Connection creditor = new Connection();
        creditor.setId(24L);
        creditor.setName("Geraldine");
        creditor.setEmail("geraldine@email.com");
        creditor.setPassword("Mo@depa2");

        /*ConnectionDTO debtorDTO = ConnectionDTO.builder()
                .id(2L)
                .name("Gerard")
                .email("gerard@email.com")
                .password("Mo@depa2")
                .build();*/

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

        /*TransactionDTO transactionDTO = TransactionDTO.builder()
                .creditor(creditorDTO)
                .debtor(debtorDTO)
                .description(transactionForm.getDescription())
                .amount(transactionForm.getAmount())
                .transactionDate(new Date())
                .build();*/

        Transactions transaction = new Transactions();
        transaction.setId(1L);
        transaction.setDescription(transactionForm.getDescription());
        transaction.setAmount(transactionForm.getAmount());
        transaction.setCreditor(creditor);
        transaction.setDebtor(debtor);
        transaction.setTransactionDate(new Date());

        /*BankAccountDTO debtorAccountDTO = BankAccountDTO.builder()
                .id(2L)
                .balance(53.00)
                .connectionBankAccount(debtorDTO)
                .build();*/

        BankAccount debtorAccount = new BankAccount();
        debtorAccount.setId(2L);
        debtorAccount.setBalance(53.00);
        debtorAccount.setConnectionBankAccount(debtor);

        /*BankAccountDTO creditorAccountDTO = BankAccountDTO.builder()
                .id(8L)
                .balance(62.00)
                .connectionBankAccount(creditorDTO)
                .build();*/

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

        /*List<TransactionDTO> transactionsDTOList = new ArrayList<>();
        transactionsDTOList.add(transactionDTO);*/

        List<Transactions> transactionsEntityList = new ArrayList<>();
        transactionsEntityList.add(transaction);

        //getCreditor
        when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(creditor));
        //when(connectionMapper.convertToDTO(creditor)).thenReturn(creditorDTO);
        //getIdentifiant
        when(connectionsRepository.findByEmail(any(String.class))).thenReturn(debtor);
        //when(connectionMapper.convertToDTO(any(Connection.class))).thenReturn(debtorDTO);
        //saveTransaction
        //////////when(transactionMapper.convertToEntity(any(TransactionDTO.class))).thenReturn(transaction);
        //////////when(transactionsRepository.save(any(Transactions.class))).thenReturn(transaction);
        //when(transactionMapper.convertToDTO(any(Transactions.class))).thenReturn(transactionDTO);
        when(transactionsService.saveTransaction(any(Transactions.class))).thenReturn(transaction);
        //getConnectionAccount
        //////////when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(debtorAccount));
        //when(bankAccountMapper.convertToDTO(any(BankAccount.class))).thenReturn(debtorAccountDTO);
        //updateDebtorAccount
        //saveBankAccount
        //when(bankAccountMapper.convertToEntity(any(BankAccountDTO.class))).thenReturn(debtorAccount);
        ////////////when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(debtorAccount);
        //getConnectionAccount
        ///////////////when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(creditorAccount));
        //when(bankAccountMapper.convertToDTO(any(BankAccount.class))).thenReturn(creditorAccountDTO);
        //updateCreditorAccount
        //saveBankAccount
        //when(bankAccountMapper.convertToEntity(any(BankAccountDTO.class))).thenReturn(creditorAccount);
        ///////////////when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(creditorAccount);
        //getTransactions
        ////////////when(transactionsRepository.findByDebtorId(any(Long.class))).thenReturn(transactionsEntityList);
        //when(transactionMapper.convertListToDTO(Collections.singletonList(any(Transactions.class)))).thenReturn(transactionsDTOList);

        //Act
        //List<TransactionDTO> transactionsDTOResponseList= connectionsService.saveTransaction(transactionForm);
        Transactions newTransactionResponse = transactionsService.saveTransaction(transaction);

        //Assert
        //////////verify(transactionsRepository,times(1)).save(transaction);
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
        /*ConnectionDTO creditorDTO = ConnectionDTO.builder()
                .id(24L)
                .name("Geraldine")
                .email("geraldined@email.com")
                .password("Mo@depa2")
                .build();*/

        Connection creditor = new Connection();
        creditor.setId(24L);
        creditor.setName("Geraldine");
        creditor.setEmail("geraldine@email.com");
        creditor.setPassword("Mo@depa2");

        /*ConnectionDTO debtorDTO = ConnectionDTO.builder()
                .id(2L)
                .name("Gerard")
                .email("gerard@email.com")
                .password("Mo@depa2")
                .build();*/

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

        /*TransactionDTO transactionDTO = TransactionDTO.builder()
                .creditor(creditorDTO)
                .debtor(debtorDTO)
                .description(transactionForm.getDescription())
                .amount(transactionForm.getAmount())
                .transactionDate(new Date())
                .build();*/

        Transactions transaction = new Transactions();
        transaction.setId(1L);
        transaction.setDescription(transactionForm.getDescription());
        transaction.setAmount(transactionForm.getAmount());
        transaction.setCreditor(creditor);
        transaction.setDebtor(debtor);
        transaction.setTransactionDate(new Date());

        /*BankAccountDTO debtorAccountDTO = BankAccountDTO.builder()
                .id(2L)
                .balance(53.00)
                .connectionBankAccount(debtorDTO)
                .build();*/

        BankAccount debtorAccount = new BankAccount();
        debtorAccount.setId(2L);
        debtorAccount.setBalance(53.00);
        debtorAccount.setConnectionBankAccount(debtor);

        /*BankAccountDTO creditorAccountDTO = BankAccountDTO.builder()
                .id(8L)
                .balance(62.00)
                .connectionBankAccount(creditorDTO)
                .build();*/

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

        /*List<TransactionDTO> transactionsDTOList = new ArrayList<>();
        transactionsDTOList.add(transactionDTO);*/

        List<Transactions> transactionsEntityList = new ArrayList<>();
        transactionsEntityList.add(transaction);

        //getCreditor
        when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(creditor));
        //when(connectionMapper.convertToDTO(creditor)).thenReturn(creditorDTO);
        //getIdentifiant
        when(connectionsRepository.findByEmail(any(String.class))).thenReturn(debtor);
        //when(connectionMapper.convertToDTO(any(Connection.class))).thenReturn(debtorDTO);
        //saveTransaction
        //when(transactionMapper.convertToEntity(any(TransactionDTO.class))).thenReturn(transaction);
        /////////////////when(transactionsRepository.save(any(Transactions.class))).thenReturn(transaction);
        //when(transactionMapper.convertToDTO(any(Transactions.class))).thenReturn(transactionDTO);
        //getConnectionAccount
        ///////////////when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(debtorAccount));
        //when(bankAccountMapper.convertToDTO(any(BankAccount.class))).thenReturn(debtorAccountDTO);
        //updateDebtorAccount
        //saveBankAccount
        //when(bankAccountMapper.convertToEntity(any(BankAccountDTO.class))).thenReturn(debtorAccount);
        ///////////////when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(debtorAccount);

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
        /*ConnectionDTO creditorDTO = ConnectionDTO.builder()
                .id(24L)
                .name("Geraldine")
                .email("geraldined@email.com")
                .password("Mo@depa2")
                .build();*/

        Connection creditor = new Connection();
        creditor.setId(24L);
        creditor.setName("Geraldine");
        creditor.setEmail("geraldine@email.com");
        creditor.setPassword("Mo@depa2");

        /*ConnectionDTO debtorDTO = ConnectionDTO.builder()
                .id(2L)
                .name("Gerard")
                .email("gerard@email.com")
                .password("Mo@depa2")
                .build();*/

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

        /*TransactionDTO transactionDTO = TransactionDTO.builder()
                .creditor(creditorDTO)
                .debtor(debtorDTO)
                .description(transactionForm.getDescription())
                .amount(transactionForm.getAmount())
                .transactionDate(new Date())
                .build();*/

        Transactions transaction = new Transactions();
        transaction.setId(1L);
        transaction.setDescription(transactionForm.getDescription());
        transaction.setAmount(transactionForm.getAmount());
        transaction.setCreditor(creditor);
        transaction.setDebtor(debtor);
        transaction.setTransactionDate(new Date());

        /*BankAccountDTO debtorAccountDTO = BankAccountDTO.builder()
                .id(2L)
                .balance(53.00)
                .connectionBankAccount(debtorDTO)
                .build();*/

        BankAccount debtorAccount = new BankAccount();
        debtorAccount.setId(2L);
        debtorAccount.setBalance(53.00);
        debtorAccount.setConnectionBankAccount(debtor);

        /*BankAccountDTO creditorAccountDTO = BankAccountDTO.builder()
                .id(8L)
                .balance(62.00)
                .connectionBankAccount(creditorDTO)
                .build();*/

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

        /*List<TransactionDTO> transactionsDTOList = new ArrayList<>();
        transactionsDTOList.add(transactionDTO);*/

        List<Transactions> transactionsEntityList = new ArrayList<>();
        transactionsEntityList.add(transaction);

        //getCreditor
        when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(creditor));
        //when(connectionMapper.convertToDTO(creditor)).thenReturn(creditorDTO);
        //getIdentifiant
        when(connectionsRepository.findByEmail(any(String.class))).thenReturn(debtor);
        //when(connectionMapper.convertToDTO(any(Connection.class))).thenReturn(debtorDTO);
        //saveTransaction
        //when(transactionMapper.convertToEntity(any(TransactionDTO.class))).thenReturn(transaction);
        ///////////////when(transactionsRepository.save(any(Transactions.class))).thenReturn(transaction);
        //when(transactionMapper.convertToDTO(any(Transactions.class))).thenReturn(transactionDTO);
        //getConnectionAccount
        ///////////////////when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(debtorAccount));
        //when(bankAccountMapper.convertToDTO(any(BankAccount.class))).thenReturn(debtorAccountDTO);
        //updateDebtorAccount
        //saveBankAccount
        //when(bankAccountMapper.convertToEntity(any(BankAccountDTO.class))).thenReturn(debtorAccount);
        /////////////when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(debtorAccount);
        /**///getConnectionAccount
        /////////////////when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(creditorAccount));
        //when(bankAccountMapper.convertToDTO(any(BankAccount.class))).thenReturn(creditorAccountDTO);
        //updateCreditorAccount
        //saveBankAccount
        //when(bankAccountMapper.convertToEntity(any(BankAccountDTO.class))).thenReturn(creditorAccount);
        ///////////////when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(creditorAccount);
        //getTransactions
        ////////////////when(transactionsRepository.findByDebtorId(any(Long.class))).thenReturn(transactionsEntityList);
        //when(transactionMapper.convertListToDTO(Collections.singletonList(any(Transactions.class)))).thenReturn(transactionsDTOList);

        //Act
        List<Transactions> transactionsResponseList =  transactionsService.getTransactions(debtor.getId());

        //Assert
        //verify(transactionsRepository,times(1)).findByDebtorId(any(Long.class));
        assertNotNull(transactionsResponseList);
        //assertEquals(transactionsDTOList.size(),transactionsDTOResponseList.size());

    }

    /**
     *
     * Should  save the transactions from the debtor/user
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void shouldSaveTransactionsIT() {
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
        debtor.setPassword("Mo@depa2");/**/

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

        Double balance = 50.00;

        List<Transactions> transactionsEntityList = new ArrayList<>();
        transactionsEntityList.add(transaction);

        //when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(creditor));
        when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(creditor));
        //when(connectionsService.getCreditor(any(Long.class))).thenReturn(creditor);
        when(connectionsService.getIdentifiant(any(String.class))).thenReturn(debtor);
        ///////////when(transactionsService.saveTransaction(any(Transactions.class))).thenReturn(transaction);
        ///////////when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(debtorAccount));
        //when(bankAccountService.getConnectionAccount(transactionForm.getIdDebtorAccount())).thenReturn(debtorAccount);
        ///////////when(bankAccountService.updateDebtorAccount(debtorAccount, any(Double.class))).thenReturn(balance);
        //bankAccountService.saveBankAccount(debtorAccount);
        ///////////when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(creditorAccount));
        //when(bankAccountService.getConnectionAccount(transactionForm.getIdCreditorAccount())).thenReturn(creditorAccount);
        ///////////when(bankAccountService.updateCreditorAccount(creditorAccount,transactionForm.getAmount())).thenReturn(balance);
        //bankAccountService.saveBankAccount(creditorAccount);
        ///////////when(transactionsService.getTransactions(debtor.getId())).thenReturn(transactionsEntityList);
        /*
        //getCreditor
        when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(creditor));
        //when(connectionMapper.convertToDTO(creditor)).thenReturn(creditorDTO);
        //getIdentifiant
        when(connectionsRepository.findByEmail(any(String.class))).thenReturn(debtor);
        //when(connectionMapper.convertToDTO(any(Connection.class))).thenReturn(debtorDTO);
        //saveTransaction
        //when(transactionMapper.convertToEntity(any(TransactionDTO.class))).thenReturn(transaction);
        ////////////when(transactionsService.saveTransaction(any(Transactions.class))).thenReturn(transaction);
        //when(transactionsRepository.save(any(Transactions.class))).thenReturn(transaction);
        //when(transactionMapper.convertToDTO(any(Transactions.class))).thenReturn(transactionDTO);
        //getConnectionAccount
        when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(debtorAccount));
        //when(bankAccountMapper.convertToDTO(any(BankAccount.class))).thenReturn(debtorAccountDTO);
        //updateDebtorAccount
        //saveBankAccount
        //when(bankAccountMapper.convertToEntity(any(BankAccountDTO.class))).thenReturn(debtorAccount);
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(debtorAccount);
        //getConnectionAccount
        when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(creditorAccount));
        //when(bankAccountMapper.convertToDTO(any(BankAccount.class))).thenReturn(creditorAccountDTO);
        //updateCreditorAccount
        //saveBankAccount
        //when(bankAccountMapper.convertToEntity(any(BankAccountDTO.class))).thenReturn(creditorAccount);
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(creditorAccount);
        //getTransactions
        when(transactionsRepository.findByDebtorId(any(Long.class))).thenReturn(transactionsEntityList);
        //when(transactionMapper.convertListToDTO(Collections.singletonList(any(Transactions.class)))).thenReturn(transactionsDTOList);
*/      /*when(connectionsService.getCreditor(any(Long.class))).thenReturn(creditor);

        when(connectionsService.getIdentifiant(any(String.class))).thenReturn(debtor);*/
        //Act
        //List<Transactions> transactionsResponseList =  transactionsService.getTransactions(debtor.getId());
        List<TransactionDTO> transactionsDTOResponseList= connectionsService.saveTransaction(transactionForm);

        //Assert
        //verify(transactionsRepository,times(1)).save(transaction);
        //assertNotNull(transactionsResponseList);
        assertNotNull(transactionsDTOResponseList);
        assertEquals(transactionsEntityList.size(),transactionsDTOResponseList.size());

    }

    /**
     *
     * Should fill the creditor accounts dropdown
     *
     */
    @Test
    public void shouldFillDropdownIT(){
        //Arrange
        List<BankAccount> userAccountList = new ArrayList<>();
        List<BankAccountDTO> userAccountDTOList = new ArrayList<>();

        /**/ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .id(2L)
                .name("Gerard")
                .email("gerard@email.com")
                .password("Mo@depa2")
                .build();

        Connection connection = new Connection();
        connection.setId(2L);
        connection.setName("Gerard");
        connection.setEmail("gerard@email.com");
        connection.setPassword("Mo@depa2");

        /**/BankAccountDTO bankAccountDTO = BankAccountDTO.builder()
                .id(1L)
                .balance(50.00)
                .connectionBankAccount(connectionDTO)
                .build();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setBalance(50.00);
        bankAccount.setConnectionBankAccount(connection);

        userAccountList.add(bankAccount);
        userAccountDTOList.add(bankAccountDTO);

        //getCreditor
        when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(connection));
        when(bankAccountRepository.findAllByConnectionBankAccountId(any(Long.class))).thenReturn(userAccountList);
        //when(bankAccountMapper.convertListToDTO(userAccountList)).thenReturn(userAccountDTOList);
        when(bankAccountService.getUserAccountsList(connection)).thenReturn(userAccountList);
        //when(connectionMapper.convertToDTO(connection)).thenReturn(connectionDTO);
        //getUserAccountsList
        //when(bankAccountRepository.findAllByConnectionBankAccountId(any(Long.class))).thenReturn(userAccountList);
        //when(bankAccountMapper.convertListToDTO(userAccountList)).thenReturn(userAccountDTOList);
        //
        //Act
        List<BankAccount> bankAccountResponseList= connectionsService.fillDropdown(2L);
        /*ConnectionDTO connectionDTOResponse = connectionsService.getCreditor(2L);
        List<BankAccountDTO> bankAccountDTOResponseList =  bankAccountService.getUserAccountsList(connectionDTOResponse);
*/
        //Assert
        //verify(connectionsRepository,times(1)).findById(2L);
        assertNotNull(bankAccountResponseList);
        assertEquals(userAccountDTOList.size(),bankAccountResponseList.size());

    }
}
