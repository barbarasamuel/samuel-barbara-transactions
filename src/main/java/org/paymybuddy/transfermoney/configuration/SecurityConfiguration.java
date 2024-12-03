package org.paymybuddy.transfermoney.configuration;

import org.paymybuddy.transfermoney.service.CustomLogoutHandler;
import org.paymybuddy.transfermoney.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 *
 * To manage the security
 *
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    /**/

    /*@Autowired
    private CustomLogoutHandler logoutHandler;*/
    private final CustomLogoutHandler logoutHandler;
    public SecurityConfiguration(CustomLogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    /*@Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;*/
   /* @Autowired
    private UserDetailsServiceImpl customUserDetailsService;*/

    /**/@Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**/@Bean
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
    /**/@Bean
    public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices(UserDetailsService userDetailsService){
        return new PersistentTokenBasedRememberMeServices("uniqueAndSecret",userDetailsService,new InMemoryTokenRepositoryImpl());
    }
    /*@Bean
    @Override
    protected UserDetailsService userDetailsService(){
        return super.userDetailsService();
    }*/


    /*@Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(myKey, userDetailsService, encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
        return rememberMe;
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
/*
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/users").hasRole("ADMIN")
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();*/
        //FormLoginConfigurer configurer = http.formLogin();


        return http
                .csrf(csrf -> {
                    csrf.disable();
                })
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> {
                    /*auth.requestMatchers("/").permitAll();*/
                    auth.requestMatchers("/error/**").permitAll();
                    auth.requestMatchers("/img/**","/css/**","/login","/register").permitAll();
                    auth.requestMatchers("/transfer","/transferTest","/profile","/contact","/test","/").authenticated();
                    /*//auth.requestMatchers("/transfer").hasRole("ADMIN");
                    auth.anyRequest().authenticated();*/
                    auth.anyRequest().permitAll();
                })
                //.httpBasic(Customizer.withDefaults())
                .formLogin(formLogin-> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/transferTest")
                        .permitAll())
                        //.successHandler(new AuthenticationSuccessHandlerCustom())
                        //.failureHandler(new AuthenticationFailureHandlerCustom()))
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutUrl("/logout"))
                .rememberMe(rememberMe -> rememberMe
                        /**/.rememberMeServices(persistentTokenBasedRememberMeServices(userDetailsService()))
                        //.rememberMeServices(persistentTokenBasedRememberMeServices(userDetailsService()))
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                        .rememberMeCookieName("remember-me"))
                        //.userDetailsService(userDetailsService()))
                /*.rememberMe((remember) -> remember
                        .rememberMeServices(rememberMeServices))*/
                /*http.authorizeRequests().and()
                .rememberMe().tokenRepository(this.persistentTokenRepository())
                .tokenValiditySeconds(1 * 1 * 2 * 60);*/
                .build();
    }

    /*@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/
    /*@Bean
    public UserDetailsService userDetailsService(){
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername("user")
        .password(encoder.encode("password"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }*/
    /*
    // Config Remember Me.
		http.authorizeRequests().and() //
				.rememberMe().tokenRepository(this.persistentTokenRepository()) //
				.tokenValiditySeconds(1 * 24 * 60 * 60);
	*/
    /*
    *@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}*/
    /*@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }*/
}
