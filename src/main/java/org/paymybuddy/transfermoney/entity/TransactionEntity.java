package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity
@Table(name="Transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTransaction")
    private Long idTransaction;

    @Column(name = "typeTransaction")
    private String typeTransaction;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "transactionDate")
    private Date transactionDate;

    @ManyToOne
    @JoinColumn(name = "idCreditorAccount")
    private BankAccountEntity creditorAccount;

    @ManyToOne
    @JoinColumn(name = "idDebtorAccount")
    private BankAccountEntity debtorAccount;

    public TransactionEntity(String description,Double amount, Date transactionDate,BankAccountEntity creditorAccount,BankAccountEntity debtorAccount){
        this.description=description;
        //...
    }
}