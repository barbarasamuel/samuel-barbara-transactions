package org.paymybuddy.transfermoney.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTest;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.ProfileForm;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTest.class})
@AutoConfigureMockMvc
public class ProfileControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ConnectionsService connectionsService;

    @Test
    public void profileSecureTest() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().isUnauthorized())
                .andReturn();

    }

    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void profileTest() throws Exception {
        ProfileForm profileForm = new ProfileForm();
        profileForm.setEmail("gerard@email.fr");
        profileForm.setOldPassword("My@ldmdp2");

        when(connectionsService.getProfile(any())).thenReturn(profileForm);

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<!DOCTYPE html>")))
                .andReturn();
    }

    @Test
    public void updateProfileTest() throws Exception {
        mockMvc.perform(post("/profile/updateEmail")
                .contentType("ProfileForm"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

    }


    @Test
    public void badOldPasswordTest() throws Exception {
        ProfileForm profileForm = new ProfileForm();
        profileForm.setEmail("gerard@email.fr");
        profileForm.setOldPassword("My@ldmdp2");
        profileForm.setNewPassword("Mo@depa2");
        profileForm.setConfirmPassword("Mo@depa2");

        ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .password("My-ldmdp2")
                .email("gerard@email.fr")
                .name("Gerard")
                .build();

        when(connectionsService.passwordUpdatingStart(any(),any())).thenReturn(connectionDTO);

        mockMvc.perform(post("/profile/updatePassword")
                .contentType("ProfileForm"))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void updatePasswordTest() throws Exception {
        ProfileForm profileForm = new ProfileForm();
        profileForm.setEmail("gerard@email.fr");
        profileForm.setOldPassword("Mo@depa2");
        profileForm.setNewPassword("My@dmdp2");
        profileForm.setConfirmPassword("My@dmdp2");

        ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .password("Mo@depa2")
                .email("gerard@email.fr")
                .name("Gerard")
                .build();

        when(connectionsService.passwordUpdatingStart(any(),any())).thenReturn(connectionDTO);

        mockMvc.perform(post("/profile/updatePassword")
                .flashAttr("ProfileForm", profileForm)
                .param("oldPassword", profileForm.getOldPassword())
                .param("newPassword", profileForm.getNewPassword())
                .param("confirmPassword", profileForm.getConfirmPassword()))
                .andExpect(status().is3xxRedirection())
                .andReturn();

    }
}
