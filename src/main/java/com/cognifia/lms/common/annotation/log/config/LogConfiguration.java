package com.cognifia.lms.common.annotation.log.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.cognifia.lms.common.annotation.log.MethodLoggingCondition;

@Slf4j
@AllArgsConstructor
@Configuration
@ConditionalOnClass(MethodLoggingCondition.class)
@ConditionalOnProperty(prefix = "application.method.log", name = "enable", havingValue = "true")
@EnableConfigurationProperties(LogProperties.class)
public class LogConfiguration {

    private LogProperties logProperties;

}
