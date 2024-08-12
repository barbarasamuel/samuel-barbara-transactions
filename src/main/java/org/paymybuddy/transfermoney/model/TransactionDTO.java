package org.paymybuddy.transfermoney.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.paymybuddy.transfermoney.entity.Connection;

import java.util.Date;

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
