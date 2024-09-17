package org.paymybuddy.transfermoney.controller;

import org.paymybuddy.transfermoney.model.BankAccountDTO;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.service.BankAccountService;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiRest {
    @Autowired
    ConnectionsService connectionsService;

    @Autowired
    BankAccountService bankAccountService;
    @GetMapping(value="/dropdown", produces = {"application/json"})
    public ResponseEntity<List<BankAccountDTO>>fillDropdownContent(@RequestParam String selectedValue, Model model){
        ConnectionDTO connectionDTO = connectionsService.getCreditor(Long.valueOf(selectedValue));
        List<BankAccountDTO> creditorAccountList = bankAccountService.getUserAccountsList(connectionDTO);

        return ResponseEntity.status(HttpStatus.OK).body(creditorAccountList);
    }
}
