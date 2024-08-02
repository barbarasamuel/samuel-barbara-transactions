package org.paymybuddy.transfermoney.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ConnectionDTO {
    private Long id;
    @NotEmpty(message = "Thanks to fill your name.")
    private String name;
    @NotEmpty(message = "Thanks to fill your email.")
    private String email;
    @NotEmpty(message = "Thanks to fill your password.")
    private String password;
    //@NotEmpty(message = "Thanks to fill your balance (min 50€.")
    //private Double balance;
}
