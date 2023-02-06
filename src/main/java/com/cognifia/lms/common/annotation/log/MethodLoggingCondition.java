package com.cognifia.lms.common.annotation.log;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MethodLoggingCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String logEnabled = context.getEnvironment().getProperty("application.method.log.enable");
        return Boolean.parseBoolean(logEnabled);
    }
}