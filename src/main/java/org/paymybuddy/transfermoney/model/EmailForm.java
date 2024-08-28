package org.paymybuddy.transfermoney.model;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmailForm {
    @Pattern(regexp = "^[A-Za-z]+@[^\\.]+\\.[A-Za-z]{2,}$",
            message = "The email must contain an at, a point and letters with no special characters")
    private String email;
}
