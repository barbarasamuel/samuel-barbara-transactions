package org.paymybuddy.transfermoney.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 *
 * To collect the information about the user profile from the client website to the server
 *
 */
@Data
public class ProfileForm {

    @Pattern(regexp = "^[A-Za-z]+@[^\\.]+\\.[A-Za-z]{2,}$",
            message = "The email must contain an at, a point and letters with no special characters")
    private String email;
    private String oldPassword;


    //@Pattern(regexp = "^((?=.*?[a-z])(?=.*?[A-Z])(?=.*?[!@#$&*])(?=.*?[0-9])){8,}$",
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])(?=.*[0-9])[A-Za-z0-9@$!%*?&]{8,8}$",
            message = "The password must contain 8 characters and at least 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
    private String newPassword;
    private String confirmPassword;
}
