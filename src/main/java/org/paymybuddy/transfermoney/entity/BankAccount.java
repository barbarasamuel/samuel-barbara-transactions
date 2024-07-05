package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Long idBankAccount;

    @OneToOne
    @JoinColumn(name="idBankAccount", referencedColumnName="idConnection",nullable=false)
    private Connection connection;

    @Column(nullable=false)
    private Double balance;

    @OneToMany(mappedBy = "creditorAccount")
    private List<Transactions> transactionsEntityList;
}
