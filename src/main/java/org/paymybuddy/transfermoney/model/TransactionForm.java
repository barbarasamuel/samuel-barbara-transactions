package org.paymybuddy.transfermoney.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * To collect the information about the user transactions from the client website to the server
 *
 */
@Data
public class TransactionForm {
    @NotEmpty(message = "Thanks to select a name.")
    private Long id;//String name;

    @NotEmpty(message = "Thanks to fill a amount.")
    private Double amount;

    @NotEmpty(message = "Thanks to fill a description.")
    private String description;

    ///////////////////////
    private Long idDebtorAccount;
    private Long idCreditorAccount;
}
