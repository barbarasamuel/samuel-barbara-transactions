package org.paymybuddy.transfermoney.controller;

import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.mapper.BankAccountMapper;
import org.paymybuddy.transfermoney.model.BankAccountDTO;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiRest {

    @Autowired
    ConnectionsService connectionsService;
    @Autowired
    BankAccountMapper bankAccountMapper;

    /**
     *
     * To load data for the list of the relation bank accounts
     *
     */
    @GetMapping(value="/dropdown", produces = {"application/json"})
    public ResponseEntity<List<BankAccountDTO>>fillDropdownContent(@RequestParam String selectedValue, Model model){

        List<BankAccount> creditorAccountList = connectionsService.fillDropdown(Long.valueOf(selectedValue));
        List<BankAccountDTO> creditorAccountDTOList = bankAccountMapper.convertListToDTO(creditorAccountList);
        return ResponseEntity.status(HttpStatus.OK).body(creditorAccountDTOList);
    }
}
