package org.paymybuddy.transfermoney.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Relation;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.repository.BankAccountRepository;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.RelationRepository;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ConnectionsServiceTests {
    @Autowired
    private ConnectionsService connectionsService;
    @Autowired
    private ConnectionsRepository connectionsRepository;
    @Autowired
    private RelationRepository relationRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;


    /**
     *
     * Should return a creditor bank accounts list
     *
     */
    @Test
    public void shouldReturnCreditorBankAccountList(){
        //Arrange
        List<BankAccount> gotCreditorBankAccountsList = new ArrayList<>();

        //Act
        gotCreditorBankAccountsList = connectionsService.fillDropdown(2L);

        //Assert
        assertEquals(2, gotCreditorBankAccountsList.size());
    }

    /**
     *
     * Should return an existing connection with the same email
     *
     */
    @Test
    public void shouldReturnConnectionWithTheSameEmail(){
        //Arrange
        RegisterForm registerForm = new RegisterForm();
        registerForm.setName("Gerard");
        registerForm.setEmail("gerard@email.fr");
        registerForm.setPassword("Mo@depa2");
        registerForm.setConfirmPassword("Mo@depa2");

        //Act
        ConnectionDTO gotExistingConnection =  connectionsService.checkEmail(registerForm.getEmail());

        //Assert
        assertEquals(registerForm.getEmail(),gotExistingConnection.getEmail());

    }

    /**
     *
     * Should return a null connection, the email should not exist
     *
     */
    @Test
    public void shouldReturnNullConnectionNotExistingEmail(){
        //Arrange
        RegisterForm registerForm = new RegisterForm();
        registerForm.setName("Gertrude");
        registerForm.setEmail("gertrude@email.com");
        registerForm.setPassword("Mo@depa2");
        registerForm.setConfirmPassword("Mo@depa2");

        //Act
        ConnectionDTO gotExistingConnection =  connectionsService.checkEmail(registerForm.getEmail());

        //Assert
        assertNull(gotExistingConnection);

    }

    /**
     *
     * Should save the transaction
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void shouldSaveTransaction(){
        //Arrange
        TransactionForm transactionForm = new TransactionForm();
        transactionForm.setId(2L);
        transactionForm.setIdCreditorAccount(24L);
        transactionForm.setDescription("Restaurant");
        transactionForm.setIdDebtorAccount(2L);
        transactionForm.setAmount(14.00);

        Connection debtor = new Connection();
        debtor.setId(2L);
        debtor.setName("Gerard");
        debtor.setEmail("gerard@email.fr");
        debtor.setPassword("Mo@depa2");

        BankAccount debtorAccount = new BankAccount();
        debtorAccount.setId(transactionForm.getIdDebtorAccount());
        debtorAccount.setConnectionBankAccount(debtor);
        debtorAccount.setBalance(50.00);
        BankAccount debttorBankAccount = bankAccountRepository.save(debtorAccount);

        Double balanceWithTransaction = debtorAccount.getBalance() - transactionForm.getAmount();
        Double expectedBalance = balanceWithTransaction - (transactionForm.getAmount()*0.5/100);
        expectedBalance = Math.round(expectedBalance * Math.pow(10,2)) / Math.pow(10,2);

        Connection creditor = new Connection();
        creditor.setId(2L);
        creditor.setName("Gerard");
        creditor.setEmail("gerard@email.fr");
        creditor.setPassword("Mo@depa2");

        BankAccount creditorAccount = new BankAccount();
        creditorAccount.setId(transactionForm.getIdCreditorAccount());
        creditorAccount.setConnectionBankAccount(creditor);
        creditorAccount.setBalance(50.00);
        BankAccount creditorBankAccount = bankAccountRepository.save(creditorAccount);

        //Act
        List <TransactionDTO> transactionsList = connectionsService.saveTransaction(transactionForm);

        //Assert
        Optional<BankAccount> gotCreditorBankAccount = bankAccountRepository.findById(24L);
        assertEquals(64.00, gotCreditorBankAccount.get().getBalance());
        Optional<BankAccount> gotDebtorBankAccount = bankAccountRepository.findById(2L);
        assertEquals(expectedBalance, gotDebtorBankAccount.get().getBalance());

        TransactionDTO lastTransactionDTO = transactionsList.get(transactionsList.size()-1);
        transactionsRepository.deleteById(lastTransactionDTO.getId());

    }

    /**
     *
     * Should get the profile
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void shouldGetProfile(){
        //Arrange
        ProfileForm expectedProfileForm = new ProfileForm();
        expectedProfileForm.setEmail("gerard@email.fr");
        expectedProfileForm.setOldPassword("Mo@depa2");
        expectedProfileForm.setNewPassword("Mo@depa1");
        expectedProfileForm.setConfirmPassword("Mo@depa1");

        //Act
        ProfileForm gotProfileForm = connectionsService.getProfile(expectedProfileForm);

        //Assert
        assertEquals(expectedProfileForm, gotProfileForm);

    }

    /**
     *
     * Should get the new user password
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void shouldPasswordUpdatingFollowing(){
        //Arrange
        ProfileForm profileForm = new ProfileForm();
        profileForm.setEmail("gerard@email.fr");
        profileForm.setOldPassword("Mo@depa2");
        profileForm.setNewPassword("Mo@depa1");
        profileForm.setConfirmPassword("Mo@depa1");

        Connection connection = new Connection();
        connection.setId(2L);
        connection.setName("Gerard");
        connection.setEmail("gerard@email.fr");
        connection.setPassword("Mo@depa2");

        //Act
        ConnectionDTO connectionDTO = connectionsService.passwordUpdatingStart(profileForm);
        connectionsService.passwordUpdatingFollowing(connectionDTO,profileForm);

        //Assert
        Connection gotConnection = connectionsRepository.findByEmail(profileForm.getEmail());
        assertNotEquals(profileForm.getOldPassword(),gotConnection.getPassword());
        connectionsRepository.save(connection);
    }

    /**
     *
     * Should return the identifiant from the email
     *
     */
    @Test
    public void shouldGetIdentifiantTest() {
        //Arrange
        Connection connection = new Connection();
        connection.setId(2L);
        connection.setName("Gerard");
        connection.setEmail("gerard@email.fr");
        connection.setPassword("Mo@depa2");

        //Act
        Connection connectionResponse = connectionsService.getIdentifiant(connection.getEmail());

        //Assert
        assertNotNull(connectionResponse);
        assertEquals(connection.getEmail(),connectionResponse.getEmail());
        assertEquals(connection.getId(),connectionResponse.getId());

    }

    /**
     *
     * Should get the id creditor
     *
     */
    @Test
    public void shouldGetCreditorTest(){
        //Arrange
        Connection connection = new Connection();
        connection.setId(2L);
        connection.setName("Gerard");
        connection.setEmail("gerard@email.fr");
        connection.setPassword("Mo@depa2");

        //Act
        Connection connectionResponse = connectionsService.getCreditor(2L);

        //Assert
        assertNotNull(connectionResponse);
        assertEquals(connection.getEmail(), connectionResponse.getEmail());
    }

    /**
     *
     * Should not expand the list when add a friend in the list because the friend already exists
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void shouldNotAddConnectionTest(){
        //Arrange
        List<RelationsConnection> gotRelationsConnectionList = new ArrayList<>();

        Connection user = new Connection();
        user.setId(2L);
        user.setName("Gerard");
        user.setEmail("gerard@email.fr");
        user.setPassword("Mo@depa2");

        Connection friend = new Connection();
        friend.setId(2L);
        friend.setName("Gerard");
        friend.setEmail("gerard@email.fr");
        friend.setPassword("Mo@depa2");

        //Act
        gotRelationsConnectionList = connectionsService.addConnection("2");

        //Assert
        assertEquals(4, gotRelationsConnectionList.size());

    }

    /**
     *
     * Should add a friend in the list
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void shouldAddConnectionTest(){

        //Arrange
        List<RelationsConnection> gotRelationsConnectionList = new ArrayList<>();

        Connection user = new Connection();
        user.setId(2L);
        user.setName("Gerard");
        user.setEmail("gerard@email.fr");
        user.setPassword("Mo@depa2");

        Connection friend = new Connection();
        friend.setId(2L);
        friend.setName("Gerard");
        friend.setEmail("gerard@email.fr");
        friend.setPassword("Mo@depa2");

        Relation existingRelation = relationRepository.findByConnectionFriendAndUser(friend,user);
        relationRepository.delete(existingRelation);

        //Act
        gotRelationsConnectionList = connectionsService.addConnection("2");

        //Assert
        assertEquals(4, gotRelationsConnectionList.size());

    }

    /**
     *
     * Should return all the connections which are in the database
     *
     */
    @Test
    public void shouldGetAllConnectionsTest(){
        //Arrange
        Iterable<Connection> connectionsList = new ArrayList<>();
        connectionsList = connectionsRepository.findAll();
        List<Connection> expectedConnectionsList = (List<Connection>) connectionsList;

        //Act
        List<Connection> connectionsResponseList = connectionsService.getAllConnections();

        //Assert
        assertEquals(expectedConnectionsList.size(),connectionsResponseList.size());
    }
}
