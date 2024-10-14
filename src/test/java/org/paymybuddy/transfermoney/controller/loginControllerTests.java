package org.paymybuddy.transfermoney.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.RequestContextFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTest.class})
@AutoConfigureMockMvc
public class loginControllerTests {
    @Autowired
    MockMvc mockMvc;

    /**
     *
     * To test there is a redirection when the username et the password are correct
     *
     */
    @Test
    public void loginTest() throws Exception {

        mockMvc.perform(post("/process-login")
                .param("username","gerard@email.com")
                .param("password","Mo@depa2"))
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
                .andExpect(status().isOk())
                .andReturn();
    }
}
