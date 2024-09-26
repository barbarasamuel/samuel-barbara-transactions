package org.paymybuddy.transfermoney.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Controller
public class LoginController {

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
    @PostMapping("/process-login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpServletRequest request){
        try{
            request.login(username,password);
            log.info("Login successful");
            return "redirect:/";
        }catch(ServletException e){
            log.error("Login failed");
            return "login";
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
