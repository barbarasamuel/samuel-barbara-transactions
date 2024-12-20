package org.paymybuddy.transfermoney.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTests;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})
@AutoConfigureMockMvc
public class ProfileControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ConnectionsService connectionsService;

    /**
     *
     * To verify the profile page can't be accessed without the identifiers
     *
     */
    @Test
    public void profileSecureTest() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

    }

    /**
     *
     * To verify the profile page is accessible with the identifiers
     *
     */
    @Test
    @WithMockUser(username="gerard@email.fr",roles={"USER"})
    public void profileTest() throws Exception {
        //Arrange
        ProfileForm profileForm = new ProfileForm();
        profileForm.setEmail("gerard@email.fr");
        profileForm.setOldPassword("My@lmdp2");

        when(connectionsService.getProfile(any())).thenReturn(profileForm);

        //Act
        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<!DOCTYPE html>")))
                .andExpect(view().name("profile"))
                .andReturn();
    }

    /**
     *
     * To verify the redirection when we modify the username
     *
     */
    @Test
    public void updateProfileTest() throws Exception {
        ProfileForm profileForm = new ProfileForm();
        profileForm.setEmail("mathieu@email.com");
        profileForm.setOldPassword("Mo@depa2");

        mockMvc.perform(post("/profile/updateEmail")
                .flashAttr("ProfileForm", profileForm)
                .param("email", profileForm.getEmail())
                .param("password", profileForm.getOldPassword())
                .contentType("ProfileForm"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andReturn();

    }

    /**
     *
     * To verify the user stays in the profile page when he/she modifies the username with a existing username
     *
     */
    @Test
    public void updateProfileExistingEmalTest() throws Exception {
        //Arrange
        ProfileForm profileForm = new ProfileForm();
        profileForm.setEmail("gerard@email.fr");
        profileForm.setOldPassword("Mo@depa2");

        ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .id(2L)
                .password("Mo@depa2")
                .email("gerard@email.fr")
                .name("Gerard")
                .build();

        when(connectionsService.checkEmail(profileForm.getEmail())).thenReturn(connectionDTO);

        //Act
        mockMvc.perform(post("/profile/updateEmail")
                .flashAttr("ProfileForm", profileForm)
                .param("email", profileForm.getEmail())
                .param("oldPassword", profileForm.getOldPassword())
                .contentType("ProfileForm"))
                .andExpect(view().name("profile"))
                .andReturn();

    }

    /**
     *
     * To verify we stay in the profile page when we try to modify the password with a wrong old password
     *
     */
    @Test
    public void badOldPasswordTest() throws Exception {
        //Arrange
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

        when(connectionsService.passwordUpdatingStart(any())).thenReturn(connectionDTO);

        //Act
        mockMvc.perform(post("/profile/updatePassword")
                .flashAttr("ProfileForm", profileForm)
                .param("email", profileForm.getEmail())
                .param("password", profileForm.getOldPassword())
                .contentType("ProfileForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andReturn();

    }

    /**
     *
     * To verify the redirection when we modify the password with good old password and good new password
     *
     */
    @Test
    public void updatePasswordTest() throws Exception {
        //Arrange
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

        when(connectionsService.passwordUpdatingStart(any())).thenReturn(connectionDTO);

        //Act
        mockMvc.perform(post("/profile/updatePassword")
                .flashAttr("ProfileForm", profileForm)
                .param("oldPassword", profileForm.getOldPassword())
                .param("newPassword", profileForm.getNewPassword())
                .param("confirmPassword", profileForm.getConfirmPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andReturn();

    }

    /**
     *
     * To verify we stay in the profile page when we modify the password with good old password and
     * a new password not meeting constraints
     *
     */
    @Test
    public void updatePasswordWithoutConstraintsTest() throws Exception {
        //Arrange
        ProfileForm profileForm = new ProfileForm();
        profileForm.setEmail("gerard@email.fr");
        profileForm.setOldPassword("Mo@depa2");
        profileForm.setNewPassword("mydmdpa");
        profileForm.setConfirmPassword("mydmdpa");

        ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .password("Mo@depa2")
                .email("gerard@email.fr")
                .name("Gerard")
                .build();

        when(connectionsService.passwordUpdatingStart(any())).thenReturn(connectionDTO);

        //Act
        mockMvc.perform(post("/profile/updatePassword")
                .flashAttr("ProfileForm", profileForm)
                .param("oldPassword", profileForm.getOldPassword())
                .param("newPassword", profileForm.getNewPassword())
                .param("confirmPassword", profileForm.getConfirmPassword()))
                .andExpect(view().name("profile"))
                .andReturn();

    }

    /**
     *
     * To verify we stay in the profile page when we modify the password with an error between the new password and
     * the confirmed password
     *
     */
    @Test
    public void updatePasswordWithDifferentNewPasswordTest() throws Exception {
        //Arrange
        ProfileForm profileForm = new ProfileForm();
        profileForm.setEmail("gerard@email.fr");
        profileForm.setOldPassword("Mo@depa2");
        profileForm.setNewPassword("My@dmdp2");
        profileForm.setConfirmPassword("Mo@dmdp2");

        ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .password("Mo@depa2")
                .email("gerard@email.fr")
                .name("Gerard")
                .build();

        when(connectionsService.passwordUpdatingStart(any())).thenReturn(connectionDTO);

        //Act
        mockMvc.perform(post("/profile/updatePassword")
                .flashAttr("ProfileForm", profileForm)
                .param("oldPassword", profileForm.getOldPassword())
                .param("newPassword", profileForm.getNewPassword())
                .param("confirmPassword", profileForm.getConfirmPassword()))
                .andExpect(view().name("profile"))
                .andReturn();

    }
}
