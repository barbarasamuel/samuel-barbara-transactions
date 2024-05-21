package org.paymybuddy.transfermoney.controller;

import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ConnectionsController {
    @Autowired
    private ConnectionsService connectionsService;
    @Autowired
    ConnectionDTO connectionDTO;

    @PostMapping("/connection")
    public String newConnection(Model model){

        model.addAttribute("connection",connectionDTO);
        return connectionsService.saveNewConnection(connectionDTO);
    }


}
