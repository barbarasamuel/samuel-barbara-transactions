package org.paymybuddy.transfermoney.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContactDTO {
    private Long id;

    private String message;

    private ConnectionDTO sender;
}
