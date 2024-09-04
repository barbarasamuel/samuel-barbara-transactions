package org.paymybuddy.transfermoney.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.security.Password;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.EmailForm;
import org.paymybuddy.transfermoney.model.PasswordForm;
import org.paymybuddy.transfermoney.model.ProfileForm;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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
    //public String updateProfile(@Valid @ModelAttribute("emailForm") EmailForm emailForm, BindingResult bindingResult, Model model){
    public String updateProfile(@Valid @ModelAttribute("profileForm") ProfileForm profileForm, BindingResult bindingResult, Model model){


        if (bindingResult.hasErrors()) {

            //model.addAttribute("emailForm", emailForm);
            model.addAttribute("profileForm", profileForm);
            log.info("Error in profile");
            return "profile";
        }

        if(Objects.equals(connectionsService.getIdentifiant(profileForm.getEmail()), "There is already an account registered with that email")) {
            bindingResult.rejectValue("email", null, "There is already an account registered with that email");
            log.info("Error in profile: email already exists");
            return "profile";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());
        connectionDTO.setEmail(profileForm.getEmail());

        connectionsService.updatedConnection(connectionDTO);

        session.invalidate();
        return "redirect:/login";
    }

    /**
     *
     * To update the password
     *
     */
    @PostMapping("/profile/updatePassword")
    //public String updatePassword(@Valid @ModelAttribute("profileForm") ProfileForm profileForm, BindingResult bindingResult, Model model){
    public String updatePassword(@Valid @ModelAttribute("profileForm") ProfileForm profileForm, BindingResult bindingResult, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());
        profileForm.setEmail(connectionDTO.getEmail());

        if (Objects.equals(connectionDTO.getPassword(), profileForm.getOldPassword())){
            if (Objects.equals(profileForm.getNewPassword(), profileForm.getConfirmPassword())) {
                if (bindingResult.hasErrors()) {

                    model.addAttribute("profileForm", profileForm);
                    log.info("Error in profile");
                    return "profile";
                }
            }else {
                bindingResult.rejectValue("confirmPassword", null, "The confirm password is different from the new password.");
                model.addAttribute("profileForm", profileForm);
                log.info("Error in new password");
                return "profile";
            }
        }else {
            bindingResult.rejectValue("oldPassword", null, "Error in the old password.");
            model.addAttribute("profileForm", profileForm);
            log.info("Error in the old password");
            return "profile";
        }
        /*if (bindingResult.hasErrors()) {
            //model.addAttribute("message", "Error in the new profile");
            return "profile";
        }*/

        /*if (bindingResult.hasErrors()) {
            model.addAttribute("profileForm", profileForm);
            log.info("Error in profile");
            return "profile";
        }

        if(Objects.equals(connectionsService.getIdentifiant(profileForm.getConfirmPassword()), "There is already an account registered with that email")) {
            bindingResult.rejectValue("confirmPassword", null, "You already have an account registered with that password");
            log.info("Error in profile: the password is not modified");
            return "profile";
        }*/


        connectionDTO.setPassword(profileForm.getConfirmPassword());

        connectionsService.updatedConnection(connectionDTO);

        session.invalidate();
        return "redirect:/login";
    }

    /**
     *
     * To access to the profile.html page and load it
     *
     */

   /* @GetMapping("/profileEmail")*/
    @GetMapping("/profile/updateEmail")
    public String loadProfileEmail(EmailForm emailForm, PasswordForm passwordForm,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());
        emailForm.setEmail(connectionDTO.getEmail());
        model.addAttribute("emailForm",emailForm);

        //////////////////////////
        passwordForm.setOldPassword(connectionDTO.getPassword());
        model.addAttribute("passwordForm",passwordForm);
        return "profile";
    }

    //@GetMapping("/profilePassword")
    @GetMapping("/profile/updatePassword")
    public String loadProfilePassword(PasswordForm passwordForm, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());
        passwordForm.setOldPassword(connectionDTO.getPassword());
        model.addAttribute("passwordForm",passwordForm);

        return "profile";
    }

/*
    @GetMapping("/profile")
    public String profile(PasswordForm passwordForm, EmailForm emailForm, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());

        emailForm.setEmail(connectionDTO.getEmail());
        //model.addAttribute("emailCurse",emailForm);
        model.addAttribute("emailForm",emailForm);
        passwordForm.setOldPassword(connectionDTO.getPassword());
        //model.addAttribute("passwordCurse",passwordForm);
        model.addAttribute("passwordForm",passwordForm);

        return "profile";
    }*/
    @GetMapping("/profile")
    public String profile(ProfileForm profileForm, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());

        profileForm.setEmail(connectionDTO.getEmail());
        profileForm.setOldPassword(connectionDTO.getPassword());
        //profileForm.setNewPassword("");

        //model.addAttribute("profileCurse",connectionDTO);
        model.addAttribute("profileForm",profileForm);
        //model.addAttribute("oldPasswordCurse",profileForm.getOldPassword());

        return "profile";
    }

}
