package org.paymybuddy.transfermoney.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RegisterForm {
    @NotEmpty(message = "Thanks to fill your name.")
    private String name;

    @NotEmpty(message = "Thanks to fill your email.")
    private String email;

    @NotEmpty(message = "Thanks to fill your password.")
    private String password;

    @NotEmpty(message = "Thanks to confirm your password.")
    private String confirmPassword;

}
