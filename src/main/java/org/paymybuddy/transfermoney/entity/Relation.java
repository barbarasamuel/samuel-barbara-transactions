package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Relation {

    @Id
    private Long idRelationEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", referencedColumnName = "idConnection",nullable=false)
    private Connection user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRelation", referencedColumnName = "idConnection",nullable=false)
    private Connection relation;
}
