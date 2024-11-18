package org.paymybuddy.transfermoney.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.model.RegisterForm;
import org.paymybuddy.transfermoney.repository.BankAccountRepository;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class RegistrationServiceTests {

    @Autowired
    RegistrationService registrationService;
    @Autowired
    ConnectionsRepository connectionsRepository;
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
        assertTrue(exception instanceof DataIntegrityViolationException);
    }

    /**
     *
     * Should save the new user (connection)
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

        //Act
        registrationService.saveRegistration(registerForm);

        //Assert
        Connection newConnection = connectionsRepository.findByEmail("gerard@myemail.fr");
        assertEquals(connection.getEmail(), newConnection.getEmail());
        BankAccount bankAccount = bankAccountRepository.findByConnectionBankAccountId(newConnection.getId());
        bankAccountRepository.deleteById(bankAccount.getId());
        connectionsRepository.deleteById(newConnection.getId());

    }
}
