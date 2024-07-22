package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Long idRelation;

    @ManyToOne
    @JoinColumn(name="idConnectionFriend", nullable=false)
    private Connection connectionFriend;

    @ManyToOne
    @JoinColumn(name="idUser", nullable=false)
    private Connection user;

    //@ManyToOne
    //@JoinColumn(name = "idRelation", nullable=false)
    //private Connection relation;

/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", referencedColumnName = "idConnection",nullable=false)
    private Connection user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRelation", referencedColumnName = "idConnection",nullable=false)
    private Connection relation;*/
}
