package org.paymybuddy.transfermoney.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * To collect the information about the user profile from the client website to the server
 *
 */
@Data
public class ProfileForm {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
