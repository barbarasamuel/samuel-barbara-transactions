package org.paymybuddy.transfermoney.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.RegisterForm;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
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

       if (bindingResult.hasErrors()) {
            model.addAttribute("registerForm", registerForm);
            log.error("Error in registration");
            return "register";
        }

        if(Objects.equals(connectionsService.getIdentifiant(registerForm.getEmail()), "There is already an account registered with that email")) {
            bindingResult.rejectValue("email", null, "There is already an account registered with that email");
            log.error("Error in registration: email already exists");
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
