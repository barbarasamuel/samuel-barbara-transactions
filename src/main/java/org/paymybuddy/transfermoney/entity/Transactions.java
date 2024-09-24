package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * To get the data about transactions
 *
 */
@Getter
@Setter
@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Long id;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private Double amount;

    @Column(nullable=false)
    private Date transactionDate;

    @ManyToOne
    @JoinColumn(name = "idCreditor",  nullable=false)
    private Connection creditor;

    @ManyToOne
    @JoinColumn(name = "idDebtor",  nullable=false)
    private Connection debtor;

    public Transactions() {
    }
    @Builder/**/
    public Transactions(String description, Double amount, Date transactionDate, Connection creditor, Connection debtor){
        this.description=description;
        this.amount=amount;
        this.transactionDate=transactionDate;
        this.creditor=creditor;
        this.debtor=debtor;
    }
}