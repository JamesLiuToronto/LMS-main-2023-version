package com.cognifia.lms.common.annotation.log;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import com.cognifia.lms.common.annotation.log.logclient.TransactionLogAdapter;

@Slf4j
@Aspect
@Component
@Conditional(MethodLoggingCondition.class)
public class MethodLogger {

    static final String REQUEST = "REQUEST";
    static final String RESPONSE = "RESPONSE";
    static final String UPDATE_USERID_NAME = "updateUserId";
    @Autowired
    TransactionLogAdapter logClient;
    @Value("${transactionlog.enable}")
    private String logEnable;

    @Around("@annotation(LogMethodData)")
    @SneakyThrows
    public Object logAroundExec(ProceedingJoinPoint pjp) {
        List<String> list = LogUtility.constructLogMsg(pjp);
        String uuid = UUID.randomUUID().toString();
        Integer updateAccountId = (Integer) getParameterByName(pjp, UPDATE_USERID_NAME);
        log.info("method {} request Parameter {}", list.get(0), list.get(1));

        persistTransactionLog(uuid, list.get(0), list.get(1), REQUEST, updateAccountId);
        Object proceed = pjp.proceed();
        persistTransactionLog(uuid, list.get(0), proceed.toString(), RESPONSE, updateAccountId);
        return proceed;
    }

    private void persistTransactionLog(String uuid, String typeCode, String message, String statusCode,
                                       int updateUserId) {
        boolean enable = Boolean.parseBoolean(logEnable);
        log.info("log.enabled=" + enable);
        if (!enable) {
            return;
        }
        log.info("startlog");
        String ret = logClient.persistTransactionLog(uuid, typeCode, message, statusCode, updateUserId);
        log.info("finishlog=" + ret);
    }

    public Object getParameterByName(ProceedingJoinPoint proceedingJoinPoint, String parameterName) {
        MethodSignature methodSig = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();
        String[] parametersName = methodSig.getParameterNames();

        int idx = Arrays.asList(parametersName).indexOf(parameterName);

        if (args.length > idx) { // parameter exist
            return args[idx];
        } // otherwise your parameter does not exist by given name
        return null;
    }

}