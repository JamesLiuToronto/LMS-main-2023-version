package com.cognifia.lms.account.security.token.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.security.token")
@Data
@Configuration
public class AuthProperties {
    String enableTokenCheck = "false" ;
    String tokenName ="access_token";
}

