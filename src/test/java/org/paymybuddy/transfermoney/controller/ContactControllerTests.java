package org.paymybuddy.transfermoney.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.ContactDTO;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureMockMvc
public class ContactControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    /**
     *
     * To test the redirection for private page when the user contacts the administrator
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void newContactTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/contact/save")
                .param("message", "Test"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    /**
     *
     * To verify the contact page can't be accessed without the identifiers
     *
     */
    @Test
    public void contactSecureTest() throws Exception {
        mockMvc.perform(get("/contact"))
                .andExpect(status().isUnauthorized())
                .andReturn();

    }

    /**
     *
     * To verify the contact page is accessible with the identifiers
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void contactTest() throws Exception {
        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<!DOCTYPE html>")))
                .andExpect(view().name("contact"))
                .andReturn();
    }

}
