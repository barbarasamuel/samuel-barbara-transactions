package org.paymybuddy.transfermoney.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTest.class})
@AutoConfigureMockMvc
public class loginControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(post("/process-login")
                .param("username","gerard@email.com")
                .param("password","Mo@depa2"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    public void loginSecureTest() throws Exception {
        mockMvc.perform(post("/process-login")
                .param("username","gerard@email.com")
                .param("password","Modepa2"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
