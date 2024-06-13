package org.paymybuddy.transfermoney.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class TransactionDTO {
    private String name;

    private Long idConnectionDebit;

    private Long idConnectionCredit;

    @NotEmpty(message = "Thanks to fill the connection field.")
    private String connection;

    @NotEmpty(message = "Thanks to fill the description field.")
    private String description;

    @NotEmpty(message = "Thanks to fill the amount field (min 1€.")
    private Double amount;
}
