package org.paymybuddy.transfermoney.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.RegisterForm;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.RegistrationService;
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
public class RegisterController {
    @Autowired
    ConnectionsService connectionsService;/**/
    @Autowired
    RegistrationService registrationService;

    /**
     *
     * To create a new user
     *
     */
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                               BindingResult bindingResult,
                               Model model){

        ConnectionDTO connectionDTO = connectionsService.checkEmail(registerForm.getEmail());
        if((connectionDTO != null) && Objects.equals(registerForm.getEmail(), connectionDTO.getEmail())){

            model.addAttribute("errorMessage","This email already exists.");
            model.addAttribute("registerForm", registerForm);
            log.error("This email already exists.");
            return "register";
        }

        if (Objects.equals(registerForm.getPassword(), registerForm.getConfirmPassword())) {
            if (bindingResult.hasErrors()) {

                model.addAttribute("profileForm", registerForm);
                log.error("Error in the password");
                return "register";
            }
        }else {
            bindingResult.rejectValue("confirmPassword", "error.registerForm", "The confirm password is different from the new password.");
            model.addAttribute("profileForm", registerForm);
            log.error("Error in new password");
            return "register";
        }

        registrationService.saveRegistration(registerForm);

        return "login";
    }

    /**
     *
     * To access to the register.html page
     *
     */
    @GetMapping("/register")
    public String registrationPage(Model model){
        model.addAttribute("registerForm",new RegisterForm());
        return "register";
    }
}
