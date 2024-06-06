package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
@Table(name="BankAccount")
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idBankAccount")
    private Long idBankAccount;

    @OneToOne
    @JoinColumn(name="idBankAccount", referencedColumnName="idConnection")
    private ConnectionsEntity connection;

    @Column(name="balance")
    private Double balance;

    @OneToMany(mappedBy = "creditorAccount")
    private List<TransactionEntity> transactionsEntityList;
}
