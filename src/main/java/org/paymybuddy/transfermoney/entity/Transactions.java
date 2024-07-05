package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Long idTransaction;

    @Column(name = "typeTransaction")
    private String typeTransaction;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private Double amount;

    @Column(nullable=false)
    private Date transactionDate;

    @ManyToOne
    @JoinColumn(name = "idCreditorAccount",nullable=false)
    private BankAccount creditorAccount;

    @ManyToOne
    //@JoinColumn()
    @JoinColumn(name = "idDebtorAccount",nullable=false)
    private BankAccount debtorAccount;

    public Transactions(String description, Double amount, Date transactionDate, BankAccount creditorAccount, BankAccount debtorAccount){
        this.description=description;
        //...
    }
}