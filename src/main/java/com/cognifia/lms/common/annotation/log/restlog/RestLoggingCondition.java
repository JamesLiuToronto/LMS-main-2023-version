package com.cognifia.lms.common.annotation.log.restlog;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RestLoggingCondition implements Condition {


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String logEnabled = context.getEnvironment().getProperty("application.rest.logging.enable");
        return Boolean.valueOf(logEnabled);
    }
}