package org.paymybuddy.transfermoney.model;


import lombok.Builder;
import lombok.Data;

/**
 *
 * DTO design pattern to collect the information about RelationDTO
 *
 */
@Builder
@Data
public class RelationDTO {
    private Long id;

    private ConnectionDTO connectionFriend;

    private ConnectionDTO user;
}
