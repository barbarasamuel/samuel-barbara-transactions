package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Data
@Entity
@Table(name="Transactions")
public class TransactionsEntity {
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

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            name="ConnectionsTransactions",
            joinColumns=@JoinColumn(name="idfkTransaction"),
            inverseJoinColumns = @JoinColumn(name="idConnectionDebit")
    )
    private Long idConnectionsEntityDebit;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            name="ConnectionsTransactions",
            joinColumns=@JoinColumn(name="idfkTransaction"),
            inverseJoinColumns = @JoinColumn(name="idConnectionCredit")
    )
    private Long idConnectionsEntityCredit;

}