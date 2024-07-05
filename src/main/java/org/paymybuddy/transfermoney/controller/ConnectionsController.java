package org.paymybuddy.transfermoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConnectionsController {
    /*@Autowired
    private ConnectionsService connectionsService;*/

    @PostMapping("/connection")
    public String newConnection(Model model){
/*
        model.addAttribute("connection",connectionDTO);
        return connectionsService.saveNewConnection(connectionDTO);*/
        return "";
    }


}
