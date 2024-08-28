package org.paymybuddy.transfermoney.model;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PasswordForm {
    private String oldPassword;

    @Pattern(regexp = "^((?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])){8,8}$",
            message = "The password must contain 8 characters and at least 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
    private String newPassword;
    private String confirmPassword;
}
