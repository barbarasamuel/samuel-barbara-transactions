package org.paymybuddy.transfermoney.controller;


import jakarta.transaction.Transactional;
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
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@Controller
public class ConnectionsController {
    @Autowired
    private ConnectionsService connectionsService;
    @Autowired
    private RelationService relationService;/**/

    /**
     *
     * To create a new friend connection
     *
     */
    @PostMapping("/connection/save")
   // @ResponseBody
    @Transactional
    /*public String newConnection(@RequestParam("hiddenAddInput") ConnectionForm connectionForm,
                                BindingResult bindingResult, Model model){*/
    /*public String newConnection(@Valid @ModelAttribute("connectionForm") ConnectionForm connectionForm,
                BindingResult bindingResult, Model model){*/
    public String newConnection( @RequestParam("friendName") String friendName, Model model, Error error){

        ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .email("@")
                .name(friendName)
                .password("00000000")
                .build();

        ConnectionDTO foundConnectionDTO = connectionsService.getConnection(connectionDTO.getName());
/**/
        if (foundConnectionDTO != null) {
            //error
            //error..rejectValue("name", null, "There is already a connection "+friendName +" with that email");
            log.error("There is already a connection "+friendName +" with that email");

        }else{

            /*connectionDTO = ConnectionDTO.builder()
                    .email("@")
                    .name(connectionForm.getFriendName())
                    .password("00000000")
                    .build();*/
/**/
            ConnectionDTO newConnectionDTO = connectionsService.newConnection(connectionDTO);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            ConnectionDTO userDTO = connectionsService.getIdentifiant(userDetails.getUsername());

            RelationDTO relationDTO = RelationDTO.builder()
                    .user(userDTO)
                    .connectionFriend(newConnectionDTO)
                    .build();

            relationService.newRelation(relationDTO);

            List<RelationsConnection> relationsListDTO = relationService.getRelations(userDTO);

            model.addAttribute("connectionsList",relationsListDTO);

        }
        /*
        model.addAttribute("connection",connectionDTO);
        return connectionsService.saveNewConnection(connectionDTO);*/
        return "redirect:/";
    }

}
