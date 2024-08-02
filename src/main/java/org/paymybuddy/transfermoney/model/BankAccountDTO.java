package org.paymybuddy.transfermoney.model;

import lombok.Builder;
import lombok.Data;
import org.paymybuddy.transfermoney.entity.Connection;

@Builder
@Data
public class BankAccountDTO {

    //private Long id;
    private ConnectionDTO connectionBankAccount;
    private Double balance;


}
