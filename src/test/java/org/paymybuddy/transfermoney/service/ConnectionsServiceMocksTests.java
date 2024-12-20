package org.paymybuddy.transfermoney.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Contact;
import org.paymybuddy.transfermoney.mapper.BankAccountMapper;
import org.paymybuddy.transfermoney.mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.ContactRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ConnectionsServiceMocksTests {
    @InjectMocks
    private ConnectionsService connectionsService;
    @Mock
    private ConnectionsRepository connectionsRepository;
    @Mock
    private ConnectionMapper connectionMapper;
    @Mock
    private RelationService relationService;
    @Mock
    private TransactionsService transactionsService;
    @Mock
    private BankAccountService bankAccountService;
    @Mock
    private BankAccountMapper bankAccountMapper;
    @InjectMocks
    private ContactService contactService;
    @Mock
    private ContactRepository contactRepository;

    /**
     *
     * Should access to the transferTest page with the information
     *
     */
    @Test
    @WithMockUser(username = "gerard@email.fr", roles = {"USER"})
    public void shouldAccessToTransferPageWithTheInformation(){
        Connection user = new Connection();
        user.setId(2L);
        user.setName("Gerard");
        user.setEmail("gerard@email.fr");
        user.setPassword("Mo@depa2");

        ConnectionDTO userDTO = ConnectionDTO.builder()
                .id(2L)
                .name("Gerard")
                .email("gerard@email.fr")
                .password("Mo@depa2")
                .build();

        List<RelationsConnection> relationsConnectionList = new ArrayList<>();
        RelationsConnection relationsConnection = new RelationsConnection(user.getId(), user.getName());
        relationsConnectionList.add(relationsConnection);

        List<TransactionsConnection> transactionsConnectionList = new ArrayList<>();
        TransactionsConnection transactionsConnection = new TransactionsConnection(
                new Date(),
                user.getName(),
                "Test",
                14.00);
        transactionsConnectionList.add(transactionsConnection);

        List< BankAccount > bankAccountList = new ArrayList<>();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(2L);
        bankAccount.setConnectionBankAccount(user);
        bankAccount.setBalance(50.00);
        bankAccountList.add(bankAccount);

        List<BankAccountDTO> bankAccountDTOList = new ArrayList<>();
        BankAccountDTO bankAccountDTO = BankAccountDTO.builder()
                .id(2L)
                .connectionBankAccount(userDTO)
                .balance(50.00)
                .build();
        bankAccountDTOList.add(bankAccountDTO);

        when(connectionsService.getIdentifiant(user.getEmail())).thenReturn(user);
        when(relationService.getRelations(user)).thenReturn(relationsConnectionList);
        when(transactionsService.getTransactionsFromUser(user)).thenReturn(transactionsConnectionList);
        when(bankAccountService.getUserAccountsList(user)).thenReturn(bankAccountList);
        when(bankAccountMapper.convertListToDTO(bankAccountList)).thenReturn(bankAccountDTOList);

        //Act
        TransferPageDTO gotInformation = connectionsService.accessTransferPage();

        //Assert
        verify(connectionsRepository, times(1)).findAllByOrderByEmailAsc();
        verify(relationService, times(1)).getRelations(user);
        verify(transactionsService, times(1)).getTransactionsFromUser(user);
        verify(bankAccountService, times(1)).getUserAccountsList(user);
        verify(bankAccountMapper, times(1)).convertListToDTO(bankAccountList);

    }

    /**
     *
     * Should update the email
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void shouldUpdateEmail(){
        //Arrange
        ProfileForm expectedProfileForm = new ProfileForm();
        expectedProfileForm.setEmail("gerard@myemail.fr");
        expectedProfileForm.setOldPassword("Mo@depa2");

        Connection connection = new Connection();
        connection.setId(2L);
        connection.setName("Gerard");
        connection.setEmail("gerard@email.fr");
        connection.setPassword("Mo@depa2");

        when(connectionsService.getIdentifiant(connection.getEmail())).thenReturn(connection);

        //Act
        connectionsService.emailUpdating(expectedProfileForm);

        //Assert
        verify(connectionsRepository, times(1)).save(connection);

    }

    /**
     *
     * Should get the user email
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void shouldPasswordUpdatingStart(){
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

        when(connectionsService.getIdentifiant("gerard@email.fr")).thenReturn(connection);

        //Act
        connectionsService.passwordUpdatingStart(profileForm);

        //Assert
        verify(connectionMapper, times(1)).convertToDTO(connection);

    }

    /**
     *
     * Should call the save method to save a message
     *
     */
    @Test
    public void shouldAddMessageTest() {
        //Arrange
        Connection connection = new Connection();
        connection.setId(2L);
        connection.setName("Gerard");
        connection.setEmail("gerard@email.fr");
        connection.setPassword("Mo@depa2");

        Contact contactTest = new Contact();
        contactTest.setId(8L);
        contactTest.setSender(connection);
        contactTest.setMessage("Message du test unitaire");

        //Act
        contactService.addedMessage(contactTest);

        //Assert
        Mockito.verify(contactRepository, times(1)).save(contactTest);

    }

}

