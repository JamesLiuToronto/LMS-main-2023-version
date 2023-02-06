package com.cognifia.lms.account.security.token.config;

import com.cognifia.lms.account.security.token.utility.JWTUtility;
import com.cognifia.lms.account.security.token.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.duration}")
    private int duration;

    @Bean
    JWTUtility jWTUtility(){
        return new JWTUtility(secret, duration);
    }

    @Bean
    TokenUtility tokenUtility(){
        return new TokenUtility(secret, duration);
    }




}
