package org.paymybuddy.transfermoney.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureMockMvc
public class LoginControllerTests {
    @Autowired
    MockMvc mockMvc;

    /**
     *
     * To test there is a redirection when the username et the password are correct with rememberMe true
     *
     */
    @Test
    public void loginWithRememberMeTest() throws Exception {

        mockMvc.perform(post("/process-login")
                .param("username","gerard@email.fr")
                .param("password","Mo@depa2")
                .param("rememberMe","true"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    /**
     *
     * To test there is a redirection when the username et the password are correct without rememberMe
     *
     */
    @Test
    public void loginWithoutRememberMeTest() throws Exception {

        mockMvc.perform(post("/process-login")
                        .param("username","gerard@email.fr")
                        .param("password","Mo@depa2")
                        .param("rememberMe","false"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    /**
     *
     * To test we stay in the login page when the password is wrong
     *
     */
    @Test
    public void loginSecureTest() throws Exception {
        mockMvc.perform(post("/process-login")
                .param("username","gerard@email.com")
                .param("password","Modepa2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andReturn();
    }

    /**
     *
     * Should go to login page
     *
     */
    @Test
    public void loginGoToLoginTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andReturn();
    }

    /**
     *
     * Should go to login page after click in the logout link
     *
     */
    @Test
    public void logoutGoToLoginTest() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"))
                .andReturn();
    }
}
