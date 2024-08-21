package org.paymybuddy.transfermoney.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**
 *
 * DTO design pattern to collect the information about TransactionsConnection
 *
 */
@Getter
@AllArgsConstructor
public class TransactionsConnection {
    private Date transactionDate;
    private String connectionName;
    private String description;
    private Double amount;

}
