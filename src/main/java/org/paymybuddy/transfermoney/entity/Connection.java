package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * To get the data about connections
 *
 */
@Getter
@Setter
@Entity
public class Connection implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @OneToMany(mappedBy = "connectionFriend")
    private List<Relation> relationAsFriend = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Relation> relationAsUser = new ArrayList<>();

    @OneToMany(mappedBy = "creditor")
    private List<Transactions> transactionsCreditor = new ArrayList<>();

    @OneToMany(mappedBy = "debtor")
    private List<Transactions> transactionsDebtor = new ArrayList<>();

    @OneToMany(mappedBy = "connectionBankAccount")
    private List<BankAccount> bankAccount = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
