package org.paymybuddy.transfermoney;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.RegisterForm;
import org.paymybuddy.transfermoney.repository.BankAccountRepository;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.service.BankAccountService;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RegistrationServiceTests {

    @Autowired
    RegistrationService registrationService;
    @Autowired
    ConnectionsRepository connectionsRepository;
    /*@InjectMocks
    ConnectionsService connectionsService;*/
    /*@Mock
    ConnectionsRepository connectionsRepository;*/
    @Autowired
    BankAccountRepository bankAccountRepository;


    /**
     *
     * Should reject the new user (connection)
     *
     */
    @Test
    public void shouldNotSaveRegistration() {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setEmail("gerard@email.fr");
        registerForm.setPassword("Mo@depa2");
        registerForm.setName("Gerard");

        //Act
        Exception exception = assertThrows(Exception.class, () -> registrationService.saveRegistration(registerForm));

        //Assert
        /*assertEquals("could not execute statement [Duplicate entry 'gerard@email.fr' for key 'connection.email_UNIQUE'] [insert into connection (email,name,password) values (?,?,?)]; SQL [insert into connection (email,name,password) values (?,?,?)]; constraint [connection.email_UNIQUE]",
                    exception.getMessage());*/
        //assertEquals(DataIntegrityViolationException.class,exception.getClass());
        assertTrue(exception instanceof DataIntegrityViolationException);
    }

    /**
     *
     * Should reject the new user (connection)
     *
     */
    @Test
    public void shouldSaveRegistration() {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setEmail("gerard@myemail.fr");
        registerForm.setPassword("Mo@depa2");
        registerForm.setName("Gerard");

        Connection connection = new Connection();
        connection.setEmail(registerForm.getEmail());
        connection.setName(registerForm.getName());
        connection.setPassword(registerForm.getPassword());

        /*Connection newConnection = new Connection();
        connection.setId(10L);
        connection.setEmail(registerForm.getEmail());
        connection.setName(registerForm.getName());
        connection.setPassword(registerForm.getPassword());

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(50.00);
        bankAccount.setConnectionBankAccount(newConnection);

        BankAccount newBankAccount = new BankAccount();
        newBankAccount.setId(45L);
        newBankAccount.setBalance(50.00);
        newBankAccount.setConnectionBankAccount(newConnection);*/

        /*when(connectionsRepository.save(connection)).thenReturn(newConnection);
        when(bankAccountRepository.save(bankAccount)).thenReturn(newBankAccount);*/

        //Act
        registrationService.saveRegistration(registerForm);

        //Assert
        Connection newConnection = connectionsRepository.findByEmail("gerard@myemail.fr");
        assertEquals(connection.getEmail(), newConnection.getEmail());
        BankAccount bankAccount = bankAccountRepository.findByConnectionBankAccountId(newConnection.getId());
        bankAccountRepository.deleteById(bankAccount.getId());
        connectionsRepository.deleteById(newConnection.getId());
        //verify(connectionsRepository, times(1)).save(newConnection);
    }
}
