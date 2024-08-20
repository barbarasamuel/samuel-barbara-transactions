package org.paymybuddy.transfermoney.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProfileForm {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
