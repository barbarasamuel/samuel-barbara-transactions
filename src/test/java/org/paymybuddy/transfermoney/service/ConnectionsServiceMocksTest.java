package org.paymybuddy.transfermoney.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Contact;
import org.paymybuddy.transfermoney.mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.mapper.ContactMapper;
import org.paymybuddy.transfermoney.mapper.RelationMapper;
import org.paymybuddy.transfermoney.model.ProfileForm;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.model.TransactionForm;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.ContactRepository;
import org.paymybuddy.transfermoney.repository.RelationRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ConnectionsServiceMocksTest {
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
        connection.setPassword("Mo@depa2");/* */

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

