package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**/@Setter
@Getter
@Entity
@Table(name="Relation")
public class RelationEntity {
/**/
    @Id
    private Long idRelationEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", referencedColumnName = "idConnection")
    private ConnectionsEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRelation", referencedColumnName = "idConnection")
    private ConnectionsEntity relation;
}
