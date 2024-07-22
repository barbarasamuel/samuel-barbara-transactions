package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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
    @JoinColumn(name = "idCreditor",  nullable=false)
    private Connection idCreditor;

    @ManyToOne
    @JoinColumn(name = "idDebtor",  nullable=false)
    private Connection idDebtor;

    /*
    @ManyToOne
    @JoinColumn(name = "idConnectionTransactions",  nullable=false)
    //@JoinColumn(name = "idConnectionTransactions", referencedColumnName="idConnection", nullable=false)
    private Connection idConnectionTransactions;
*/
    /*@ManyToOne
    @JoinColumn(name = "idCreditorAccount",  nullable=false)
    //@JoinColumn(name = "idCreditorAccount", referencedColumnName="idConnection", nullable=false)
    private BankAccount creditorAccount;

    @ManyToOne
    @JoinColumn(name = "idDebtorAccount",nullable=false)
    //@JoinColumn(name = "idDebtorAccount", referencedColumnName="idConnection",nullable=false)
    private BankAccount debtorAccount;*/

    public Transactions(String description, Double amount, Date transactionDate, BankAccount creditorAccount, BankAccount debtorAccount){
        this.description=description;
        //...
    }
}