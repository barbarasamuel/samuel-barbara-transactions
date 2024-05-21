package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="Connections")
public class ConnectionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idConnection")
    private Long idConnection;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="balance")
    private Double balance;

    @ManyToMany(mappedBy="Connections")
    private List<TransactionsEntity> transactionsEntityList;

}
