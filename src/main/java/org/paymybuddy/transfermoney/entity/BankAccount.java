package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * To get the data about bank accounts
 *
 */
@Getter
@Setter
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Long id;

    @Column(nullable=false)
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "idConnectionBank",  nullable=false)
    private Connection connectionBankAccount;

}
