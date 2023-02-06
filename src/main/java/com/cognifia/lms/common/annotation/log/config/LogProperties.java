package com.cognifia.lms.common.annotation.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "application.method.log")
@Data
@Configuration
public class LogProperties {
    String enable = "false";
}

