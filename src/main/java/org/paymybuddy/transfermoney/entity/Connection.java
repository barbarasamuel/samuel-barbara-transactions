package org.paymybuddy.transfermoney.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
public class Connection implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Long idConnection;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

   /* @OneToMany(fetch = FetchType.LAZY)
    //@JoinTable(name="RelationEntity", joinColumns=@JoinColumn(name="idUser"), inverseJoinColumns=@JoinColumn(name="idRelation"))
    @JoinTable(name="RelationEntity", joinColumns=@JoinColumn(name="idUser"), inverseJoinColumns=@JoinColumn(name="idRelationEntity"))
    private List<ConnectionsEntity> relations = new ArrayList<>();*/

    @ManyToMany
    @JoinTable(name="Relation", joinColumns=@JoinColumn(name="idUser",nullable=false), inverseJoinColumns=@JoinColumn(name="idConnection"))
    private List<Connection> relations = new ArrayList<>();

    @OneToOne
    @JoinColumn(name="idConnection", referencedColumnName="idBankAccount",nullable=false)
    private BankAccount bankAccount;

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
