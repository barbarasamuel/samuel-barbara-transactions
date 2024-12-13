package org.paymybuddy.transfermoney.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureMockMvc
public class TransactionControllerTests {
    @Autowired
    MockMvc mockMvc;


    /**
     *
     * To verify the transferTest page can't be accessed without the identifiers
     *
     */
    @Test
    public void transferSecureTest() throws Exception {
        mockMvc.perform(get("/transfer"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

    }

    /**
     *
     * To verify the transferTest page is accessible with the identifiers
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void transferTest() throws Exception {

        mockMvc.perform(get("/transfer"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<!DOCTYPE html>")))
                .andReturn();
    }
}
