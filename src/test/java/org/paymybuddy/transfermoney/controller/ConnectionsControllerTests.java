package org.paymybuddy.transfermoney.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ConnectionsControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addNewConnectionTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/connection/list"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
