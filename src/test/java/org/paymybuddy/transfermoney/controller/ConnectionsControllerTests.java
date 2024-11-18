package org.paymybuddy.transfermoney.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureMockMvc
public class ConnectionsControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ConnectionsService connectionsService;

    /**
     *
     * To test the redirection when we add a friend
     *
     */
    @Test
    public void addNewConnectionTest() throws Exception {

        // Act
        MvcResult result = mockMvc.perform(post("/connection/list")
                .param("friendName","elise@monemail.fr"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

    }

    /**
     *
     * Should go to redirect:/
     *
     */
    @Test
    public void shouldGoToRedirectTest() throws Exception {

        // Act
        MvcResult result = mockMvc.perform(post("/connection/list")
                .param("friendName","elise@monemail.fr"))
                .andExpect(view().name("redirect:/"))
                .andReturn();

    }

    /**
     *
     * Should return a list not null
     *
     */
   /* @Test
    public void shouldReturnListNotNullTest() throws Exception {

        // Act
        MvcResult result = mockMvc.perform(post("/connection/list")
                        .param("friendName","elise@monemail.fr"))
                .andExpect(view().name("redirect:/"))
                .andReturn();

    }*/
}
