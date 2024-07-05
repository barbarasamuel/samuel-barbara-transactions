package org.paymybuddy.transfermoney.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model){

        //model.addAttribute("connection",connectionDTO);
        return "login";
    }


    @PostMapping("/process-login")
    /*public String handleLogin(){

    }*/
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpServletRequest request){
        try{
            request.login(username,password);
            return "redirect:/";
        }catch(ServletException e){
            return "login";
        }
    }
    @GetMapping("/")
    public String home(){return "transfer";}

}
