package org.paymybuddy.transfermoney.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TransactionForm {
    @NotEmpty(message = "Thanks to select a name.")
    private Long id;//String name;

    @NotEmpty(message = "Thanks to fill a amount.")
    private Double amount;

    @NotEmpty(message = "Thanks to fill a description.")
    private String description;
}
