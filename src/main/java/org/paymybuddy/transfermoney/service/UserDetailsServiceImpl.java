package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.entity.Connection;
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


    /**
     *
     * To load the information user
     *
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Connection connection = connectionsRepository.findByEmail(username);
        if(connection==null){
            throw new UsernameNotFoundException("Thanks to connect with the new identifiants");
        }else {
            connection.setPassword(passwordEncoder.encode(connection.getPassword()));
            return connection;
        }
    }

}
