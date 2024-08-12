package org.paymybuddy.transfermoney.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.paymybuddy.transfermoney.entity.Connection;
@Builder
@Data
public class RelationDTO {
    private Long id;

    private ConnectionDTO connectionFriend;

    private ConnectionDTO user;
}
