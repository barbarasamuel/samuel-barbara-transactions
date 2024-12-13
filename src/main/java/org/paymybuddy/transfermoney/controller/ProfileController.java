package org.paymybuddy.transfermoney.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.ProfileForm;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String updateProfile(@Valid @ModelAttribute("profileForm") ProfileForm profileForm, BindingResult bindingResult, Model model){


        if (bindingResult.hasErrors()) {

            model.addAttribute("profileForm", profileForm);
            log.error("Error in the email");
            return "profile";
        }

        ConnectionDTO connectionDTO = connectionsService.checkEmail(profileForm.getEmail());
        if((connectionDTO != null) && profileForm.getEmail().equals(connectionDTO.getEmail())){

            bindingResult.rejectValue("email", null, "There is already an account registered with that email");
            log.error("Error in profile: email already exists");
            return "profile";
        }

        connectionsService.emailUpdating(profileForm);

        session.invalidate();
        return "redirect:/login?updating";
    }

    /**
     *
     * To update the password
     *
     */
    @PostMapping("/profile/updatePassword")
    public String updatePassword(@Valid @ModelAttribute("profileForm") ProfileForm profileForm, BindingResult bindingResult, Model model){

        ConnectionDTO connectionDTO = connectionsService.passwordUpdatingStart(profileForm);

        if (connectionDTO.getPassword().equals(profileForm.getOldPassword())){
            if (profileForm.getNewPassword().equals(profileForm.getConfirmPassword())) {
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
