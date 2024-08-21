package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * To get the data about relations
 *
 */
@Setter
@Getter
@Entity
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="idConnectionFriend", nullable=false)
    private Connection connectionFriend;

    @ManyToOne
    @JoinColumn(name="idUser", nullable=false)
    private Connection user;

}
