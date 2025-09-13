package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.service.impl.SecurityCustomUserDetailService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OAuthAuthenticationHandler oAuthAuthenticationHandler;

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // url for public and private user 
        httpSecurity.authorizeHttpRequests(authorise -> {
            // authorise.requestMatchers("/home","/signup").permitAll();
            authorise.requestMatchers(("/user/**")).authenticated();
            authorise.anyRequest().permitAll();
        });
        // form default values are we using 
        // if we need to configure anything related to form security then we come here
        httpSecurity.formLogin(formlogin->{
            formlogin.loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .successForwardUrl("/user/dashboard")
            .failureForwardUrl("/login?error=true")
            .usernameParameter("email")
            .passwordParameter("password");
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutform -> {
            logoutform.logoutUrl("/do-logout");
            logoutform.logoutSuccessUrl("/login?logout=true");
        });
        httpSecurity.oauth2Login(oauth2 -> {
            oauth2.loginPage("/login");
            oauth2.successHandler(oAuthAuthenticationHandler);
        });

        return httpSecurity.build();
          
    }
}
