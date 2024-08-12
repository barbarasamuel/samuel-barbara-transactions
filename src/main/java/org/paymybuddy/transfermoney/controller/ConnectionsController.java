package org.paymybuddy.transfermoney.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class ConnectionsController {
    @Autowired
    private ConnectionsService connectionsService;
    @Autowired
    private RelationService relationService;/**/

    @PostMapping("/connection/save")
    public String newConnection(@Valid @ModelAttribute("connectionForm") ConnectionForm connectionForm,
                                BindingResult bindingResult, Model model){
        ConnectionDTO connectionDTO = connectionsService.getConnection(connectionForm.getName());
        if (connectionDTO != null) {

            bindingResult.rejectValue("name", null, "There is already a connection "+connectionForm.getName() +" with that email");
            log.error("There is already a connection "+connectionForm.getName() +" with that email");

        }else{

            connectionDTO = ConnectionDTO.builder()
                    .email("@")
                    .name(connectionForm.getName())
                    .password("00000000")
                    .build();

            connectionsService.newConnection(connectionDTO);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            ConnectionDTO userDTO = connectionsService.getIdentifiant(userDetails.getUsername());
            List<RelationsConnection> relationsListDTO = relationService.getRelations(userDTO);

            model.addAttribute("connectionsList",relationsListDTO);

        }
        /*
        model.addAttribute("connection",connectionDTO);
        return connectionsService.saveNewConnection(connectionDTO);*/
        return "transferTest";
    }


}
