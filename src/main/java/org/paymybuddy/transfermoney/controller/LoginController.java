package org.paymybuddy.transfermoney.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Controller
public class LoginController {
   /**/ @Autowired
    private PersistentTokenBasedRememberMeServices rememberMeServices;

    /**
     *
     * To access to the login.html page
     *
     */
    @GetMapping("/login")
    public String login(Model model){

        return "login";
    }

    /**
     *
     * To access to the private web pages
     *
     */
    /*@PostMapping("/process-login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpServletRequest request){
        try{
            request.login(username,password);
            log.info("Login successful");
            return "redirect:/";
        }catch(ServletException e){
            log.error("Login failed");
            return "login";
        }
    }*/
    /*@PostMapping("/process-login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpServletRequest request){
        try{
            request.login(username,password);
            log.info("Login successful");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/";
        }catch(ServletException e){
            log.error("Login failed");
            return "login";
        }
    }*/
    /**/
    @PostMapping("/process-login")
    public String handleLogin(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(name="remember-me",defaultValue="false") boolean rememberMe,
            HttpServletRequest request,
            HttpServletResponse response){

        try{
            request.login(username,password);
            if(rememberMe){
                rememberMeServices.loginSuccess(request,response, (Authentication) request.getUserPrincipal());
            }
            log.info("Login successful");
            return "redirect:/";
        }catch (Exception e){
            log.error("Login failed");
            return "redirect:/login?error";
        }
    }


    /**
     *
     * To log out
     *
     */
    @GetMapping("/logout")
    public String logout(){
        log.info("Logout successful");
        return "redirect:/login?logout";
    }
}
