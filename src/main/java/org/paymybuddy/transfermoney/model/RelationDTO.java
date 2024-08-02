package org.paymybuddy.transfermoney.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.paymybuddy.transfermoney.entity.Connection;

public class RelationDTO {
    private Long id;

    private Connection connectionFriend;

    private Connection user;
}
