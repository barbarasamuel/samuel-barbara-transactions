package org.paymybuddy.transfermoney.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApiRestTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     *
     * Should return creditor account number
     *
     */
    @Test
    void apiRestShouldReturnCreditorAccountDTOListInJSONTest() throws Exception {

        this.mockMvc.perform(get("/dropdown?selectedValue=2").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.[1].id").value(24));
    }

}
