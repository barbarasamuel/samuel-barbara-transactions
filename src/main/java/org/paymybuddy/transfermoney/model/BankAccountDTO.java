package org.paymybuddy.transfermoney.model;

import lombok.Builder;
import lombok.Data;

/**
 *
 * DTO design pattern to collect the information about BankAccountDTO
 *
 */
@Builder
@Data
public class BankAccountDTO {

    //private Long id;
    private ConnectionDTO connectionBankAccount;
    private Double balance;


}
