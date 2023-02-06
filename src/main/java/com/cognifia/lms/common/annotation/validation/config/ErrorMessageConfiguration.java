package com.cognifia.lms.common.annotation.validation.config;

import com.cognifia.lms.common.annotation.validation.errormessage.ErrorMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ErrorMessageConfiguration {

    @Value("${application.translation.properties.baseName}")
    private String propertiesBasename;
/*
    @Value("${application.translation.properties.defaultLocale}")
    private String defaultLocale;

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(new Locale(defaultLocale));
        return acceptHeaderLocaleResolver;
    }
*/
    @Bean(name = "errorResourceBundleMessageSource")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename(propertiesBasename);
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }

    @Bean
    public ErrorMessage getErrorMessage(){
        return new ErrorMessage(messageSource());
    }

    @Bean
    public com.cognifia.lms.common.annotation.validation.exception.BusinessExceptionHandler getBusinessExceptionHandler(){
        return new com.cognifia.lms.common.annotation.validation.exception.BusinessExceptionHandler();
    }

    @Bean
    public com.cognifia.lms.common.annotation.validation.exception.AuthorizeExceptionHandler getAuthorizeExceptionHandler(){
        return new com.cognifia.lms.common.annotation.validation.exception.AuthorizeExceptionHandler();
    }
}
