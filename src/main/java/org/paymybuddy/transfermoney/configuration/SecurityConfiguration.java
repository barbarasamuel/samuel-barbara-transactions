package org.paymybuddy.transfermoney.configuration;

import org.paymybuddy.transfermoney.service.CustomLogoutHandler;
import org.paymybuddy.transfermoney.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;

/**
 *
 * To manage the security
 *
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final CustomLogoutHandler logoutHandler;
    public SecurityConfiguration(CustomLogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }


    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices(UserDetailsService userDetailsService){
        return new PersistentTokenBasedRememberMeServices("uniqueAndSecret",userDetailsService,new InMemoryTokenRepositoryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        return http
                .csrf(csrf -> {
                    csrf.disable();
                })
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/error/**").permitAll();
                    auth.requestMatchers("/img/**","/css/**","/login","/register").permitAll();
                    auth.requestMatchers("/transfer","/transferTest","/profile","/contact","/test","/").authenticated();
                    auth.anyRequest().permitAll();
                })
                .formLogin(formLogin-> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/transferTest")
                        .permitAll())
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutUrl("/logout"))
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeServices(persistentTokenBasedRememberMeServices(userDetailsService()))
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                        .rememberMeCookieName("remember-me"))
                //.tokenValiditySeconds(1 * 24 * 60 * 60);
                .build();
    }

}
