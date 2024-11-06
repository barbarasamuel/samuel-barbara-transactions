package org.paymybuddy.transfermoney.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
import org.paymybuddy.transfermoney.model.ProfileForm;
import org.paymybuddy.transfermoney.model.RegisterForm;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureMockMvc
public class RegisterControllerTests {
    @Autowired
    MockMvc mockMvc;

    /*@MockBean
    private ConnectionsService connectionsService;*/

    /**
     *
     * To verify the register page is accessible without the identifiers
     *
     */
    @Test
    public void registerSecureTest() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
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
                .andReturn();
    }

}
