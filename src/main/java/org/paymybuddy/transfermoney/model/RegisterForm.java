package org.paymybuddy.transfermoney.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * To collect the information about the user register from the client website to the server
 *
 */
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
