package org.paymybuddy.transfermoney.model;

import lombok.Builder;
import lombok.Data;

/**
 *
 * DTO design pattern to collect the information about ContactDTO
 *
 */
@Builder
@Data
public class ContactDTO {
    private Long id;

    private String message;

    private ConnectionDTO sender;
}
