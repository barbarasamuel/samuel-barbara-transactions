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
    /*

    @NotEmpty(message = "Thanks to fill the connection field.")
    private String connection;

    @NotEmpty(message = "Thanks to fill the description field.")
    private String description;

    @NotEmpty(message = "Thanks to fill the amount field (min 1€.")
    private Double amount;*/
    private Long id;
    private String description;
    private Double amount;
    private Date transactionDate;
    private ConnectionDTO creditor;
    private ConnectionDTO debtor;

}
