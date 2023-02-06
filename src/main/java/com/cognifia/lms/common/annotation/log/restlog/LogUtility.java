package com.cognifia.lms.common.annotation.log.restlog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogUtility {
    public static List<String> constructLogMsg(JoinPoint jp) {
        String args = Arrays.asList(jp.getArgs()).stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        List<String> list = new ArrayList<>() ;
        list.add(method.getName());
        list.add(args);
        return list;
    }

}
