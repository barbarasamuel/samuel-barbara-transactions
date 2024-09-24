package org.paymybuddy.transfermoney.model;

import lombok.Builder;
import lombok.Data;


import java.util.Date;

/**
 *
 * DTO design pattern to collect the information about TransactionDTO
 *
 */
@Builder
@Data
public class TransactionDTO {

    private Long id;
    private String description;
    private Double amount;
    private Date transactionDate;
    private ConnectionDTO creditor;
    private ConnectionDTO debtor;

}
