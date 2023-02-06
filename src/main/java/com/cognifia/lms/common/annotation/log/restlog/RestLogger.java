package com.cognifia.lms.common.annotation.log.restlog;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.List;


@Aspect
@Slf4j
@Component
@Conditional(RestLoggingCondition.class)
public class RestLogger {

    private static final String POINTCUT = "within(com.cognifia.lms.account.api.*)";
    @Around(POINTCUT)
    @SneakyThrows
    public Object logArroundExec(ProceedingJoinPoint pjp) {
        List<String> list = LogUtility.constructLogMsg(pjp) ;
        log.info("rest before {} with input {}", list.get(0), list.get(1));
        long start = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        long end = System.currentTimeMillis();
        String result = null;
        if (proceed != null)
            result = proceed.toString();
        log.info("rest after {} with ({}) result: {}", list.get(0), (end-start), result);
        return proceed;
    }

    @AfterThrowing(pointcut = POINTCUT, throwing = "e")
    public void logAfterException(JoinPoint jp, Exception e) {
        List<String> list = LogUtility.constructLogMsg(jp) ;
        log.error("rest Exception method {} with {} with ex: {}", list.get(0), list.get(1),  e.toString());
    }

    /*
    private String constructLogMsg(JoinPoint jp) {
        var args = Arrays.asList(jp.getArgs()).stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        var sb = new StringBuilder("@");
        sb.append(method.getName());
        sb.append(":");
        sb.append(args);
        return sb.toString();
    }

     */
}