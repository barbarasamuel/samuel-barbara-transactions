package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConnectionsRepository connectionsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ConnectionsEntity connectionsEntity = connectionsRepository.findByEmail(username);
        connectionsEntity.setPassword(passwordEncoder.encode(connectionsEntity.getPassword()));
        return connectionsEntity;
    }
}
