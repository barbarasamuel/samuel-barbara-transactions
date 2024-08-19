package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Long id;

    @Column(nullable=false)
    private String message;

    @ManyToOne
    @JoinColumn(name="idSender", nullable=false)
    private Connection sender;
}
