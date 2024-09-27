package org.paymybuddy.transfermoney.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.ProfileForm;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Slf4j
@Controller
public class ProfileController {
    @Autowired
    private HttpSession session;
    @Autowired
    ConnectionsService connectionsService;

    /**
     *
     * To update the email
     *
     */
    @PostMapping("/profile/updateEmail")
    @Transactional
    public String updateProfile(@Valid @ModelAttribute("profileForm") ProfileForm profileForm, BindingResult bindingResult, Model model){


        if (bindingResult.hasErrors()) {

            model.addAttribute("profileForm", profileForm);
            log.error("Error in the email");
            return "profile";
        }

        if(Objects.equals(connectionsService.getIdentifiant(profileForm.getEmail()), "There is already an account registered with that email")) {
            bindingResult.rejectValue("email", null, "There is already an account registered with that email");
            log.error("Error in profile: email already exists");
            return "profile";
        }

        connectionsService.emailUpdating(profileForm);

        session.invalidate();
        return "redirect:/login";
    }

    /**
     *
     * To update the password
     *
     */
    @PostMapping("/profile/updatePassword")
    @Transactional
    public String updatePassword(@Valid @ModelAttribute("profileForm") ProfileForm profileForm, BindingResult bindingResult, Model model){

        ConnectionDTO connectionDTO = connectionsService.passwordUpdatingStart(profileForm, bindingResult);

        if (Objects.equals(connectionDTO.getPassword(), profileForm.getOldPassword())){
            if (Objects.equals(profileForm.getNewPassword(), profileForm.getConfirmPassword())) {
                if (bindingResult.hasErrors()) {

                    model.addAttribute("profileForm", profileForm);
                    log.error("Error in the password");
                    return "profile";
                }
            }else {
                bindingResult.rejectValue("confirmPassword", "error.profileForm", "The confirm password is different from the new password.");
                model.addAttribute("profileForm", profileForm);
                log.error("Error in new password");
                return "profile";
            }
        }else {
            bindingResult.rejectValue("oldPassword", "error.profileForm", "Error in the old password.");
            model.addAttribute("profileForm", profileForm);
            log.error("Error in the old password");
            return "profile";
        }

        connectionsService.passwordUpdatingFollowing(connectionDTO,profileForm);

        session.invalidate();
        return "redirect:/login";
    }

    /**
     *
     * To access to the profile.html page and load it
     *
     */

    @GetMapping("/profile")
    public String profile(ProfileForm profileForm, Model model){

        profileForm = connectionsService.getProfile(profileForm);

        model.addAttribute("profileForm",profileForm);

        return "profile";
    }

}
