package org.paymybuddy.transfermoney.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.ProfileForm;
import org.paymybuddy.transfermoney.model.RegisterForm;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.repository.BankAccountRepository;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegisterControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ConnectionsService connectionsService;

    @MockBean
    private ConnectionsRepository connectionsRepository;

    @MockBean
    private BankAccountRepository bankAccountRepository;


    /**
     *
     * To verify the register page is accessible without the identifiers
     *
     */
    @Test
    public void registerSecureTest() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andReturn();

    }

    /**
     *
     * To verify the existence of the registerForm and access to the register page without the identifiers
     *
     */
    @Test
    public void registerTest() throws Exception {

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("registerForm"))
                .andExpect(model().attribute("registerForm",instanceOf(RegisterForm.class)))
                .andExpect(content().string(containsString("<!DOCTYPE html>")))
                .andExpect(view().name("register"))
                .andReturn();
    }

    /**
     *
     * If it is a double email we go back to the register page
     *
     */
    @Test
    public void registerDoubleEmailTest() throws Exception {
        //Arrange
        RegisterForm registerForm = new RegisterForm();
        registerForm.setName("Gerard");
        registerForm.setEmail("gerard@email.fr");
        registerForm.setPassword("Mo@depa2");
        registerForm.setConfirmPassword("Mo@depa2");

        ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .password("Mo@depa2")
                .email("gerard@email.fr")
                .name("Gerard")
                .build();

        when(connectionsService.checkEmail(registerForm.getEmail())).thenReturn(connectionDTO);

        //Act
        mockMvc.perform(post("/register/save")
                .flashAttr("RegisterForm", registerForm)
                .param("name", registerForm.getName())
                .param("email", registerForm.getEmail())
                .param("password", registerForm.getPassword())
                .param("confirmPassword", registerForm.getConfirmPassword())
                .contentType("RegisterForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andReturn();

    }

    /**
     *
     * If the confirmed password is different from the first password entry, the user goes back to the register page
     *
     */
    @Test
    public void registerDifferentPasswordTest() throws Exception {
        //Arrange
        RegisterForm registerForm = new RegisterForm();
        registerForm.setName("Mathieu");
        registerForm.setEmail("mathieu@email.com");
        registerForm.setPassword("Mo@depa2");
        registerForm.setConfirmPassword("Modepa2");

        ConnectionDTO connectionDTO = ConnectionDTO.builder().build();

        when(connectionsService.checkEmail(registerForm.getEmail())).thenReturn(connectionDTO);

        //Act
        mockMvc.perform(post("/register/save")
                .flashAttr("RegisterForm", registerForm)
                .param("name", registerForm.getName())
                .param("email", registerForm.getEmail())
                .param("password", registerForm.getPassword())
                .param("confirmPassword", registerForm.getConfirmPassword())
                .contentType("RegisterForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andReturn();

    }

    /**
     *
     * If password constraints are not met, the user goes back to the register page
     *
     */
    @Test
    public void registerNotCorrectPasswordTest() throws Exception {
        //Arrange
        RegisterForm registerForm = new RegisterForm();
        registerForm.setName("Mathieu");
        registerForm.setEmail("mathieu@email.com");
        registerForm.setPassword("Modepa2");
        registerForm.setConfirmPassword("Modepa2");

        ConnectionDTO connectionDTO = ConnectionDTO.builder().build();

        when(connectionsService.checkEmail(registerForm.getEmail())).thenReturn(connectionDTO);

        //Act
        mockMvc.perform(post("/register/save")
                .flashAttr("RegisterForm", registerForm)
                .param("name", registerForm.getName())
                .param("email", registerForm.getEmail())
                .param("password", registerForm.getPassword())
                .param("confirmPassword", registerForm.getConfirmPassword())
                .contentType("RegisterForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andReturn();

    }

    /**
     *
     * If the registerForm is correct, the user goes back to the login page
     *
     */
    @Test
    public void correctRegisterTest() throws Exception {
        //Arrange
        RegisterForm registerForm = new RegisterForm();
        registerForm.setName("Mathieu");
        registerForm.setEmail("mathieu@email.com");
        registerForm.setPassword("Mo@depa2");
        registerForm.setConfirmPassword("Mo@depa2");

        ConnectionDTO connectionDTO = ConnectionDTO.builder().build();

        when(connectionsService.checkEmail(registerForm.getEmail())).thenReturn(connectionDTO);

        //Act
        mockMvc.perform(post("/register/save")
                .flashAttr("RegisterForm", registerForm)
                .param("name", registerForm.getName())
                .param("email", registerForm.getEmail())
                .param("password", registerForm.getPassword())
                .param("confirmPassword", registerForm.getConfirmPassword())
                .contentType("RegisterForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andReturn();

    }
}
