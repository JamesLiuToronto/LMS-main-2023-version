package com.cognifia.lms.account.security.token;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.cognifia.lms.account.infrastructure.adapter.UserAccountAdapter;
import com.cognifia.lms.account.model.SpecialGroupCodeTypeConstant;
import com.cognifia.lms.account.security.token.utility.UserUtility;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import com.cognifia.lms.account.security.token.model.CurrentUser;
import com.cognifia.lms.common.security.LoginUserInfoUtility;

@Slf4j
@Aspect
@Component
@Conditional(AuthorizeCondition.class)
public class AuthorizeUserAspect {

    @Autowired
    UserAccountAdapter userAccountAdapter;

    static final String INPUT_USERID_NAME = "userAccountId";

    static String AUTH_ERROR = "AUTHORIZE.INVALID";
    @Value("${application.security.updateuserid_match_check}")
    String checkUpdateUerIdMatch;

    @Around("@annotation(authorizeUser)")
    public Object authorize(ProceedingJoinPoint joinPoint, AuthorizeUser authorizeUser) throws Throwable {
        Object[] args = joinPoint.getArgs();
        //BEFORE METHOD EXECUTION

        CurrentUser user = UserUtility.getCurrentUser();

        checkAccessRight(joinPoint, authorizeUser, user);

        //This is where ACTUAL METHOD will get invoke
        return joinPoint.proceed();


    }

    private void checkAccessRight(ProceedingJoinPoint joinPoint, AuthorizeUser authorizeUser, CurrentUser user) {

        String groupCode = getGroupCode(authorizeUser);
        String methodName = getMerthodName(joinPoint);
        boolean acl = userAccountAdapter.checkMethodACLByGroup(user.getUserId(), methodName, groupCode) ;
        if (acl) { return ;}

        if (groupCode.equalsIgnoreCase(SpecialGroupCodeTypeConstant.SELF)){
            checkSameUserAccount(joinPoint);
            return ;
        }

        if (groupCode.equalsIgnoreCase(SpecialGroupCodeTypeConstant.FAMILY)){
            checkSameFamilyAccount(joinPoint);
            return ;
        }

        throw new AuthorizeException(AUTH_ERROR);

    }

    private String getGroupCode(AuthorizeUser authorizeUser){
        String[] requiredList = authorizeUser.requiredRoles();
        if ((requiredList == null)||(requiredList.length == 0)){
            return null ;
        }
        return requiredList[0] ;

    }

    private void checkSameUserAccount(ProceedingJoinPoint joinPoint) {
        Integer userAccountId = (Integer) getParameterByName(joinPoint, INPUT_USERID_NAME);
        int updateAccountId = LoginUserInfoUtility.getLoginUserId();
        log.info("userAccountId=" + userAccountId);
        log.info("updateAccountId=" + updateAccountId);
        if (userAccountId.intValue() == updateAccountId) {return;}
        throw new AuthorizeException("update-userid.validation");
    }

    private void checkSameFamilyAccount(ProceedingJoinPoint joinPoint) {
        Integer userAccountId = (Integer) getParameterByName(joinPoint, INPUT_USERID_NAME);
        int updateAccountId = LoginUserInfoUtility.getLoginUserId();
        log.info("userAccountId=" + userAccountId);
        log.info("updateAccountId=" + updateAccountId);
        if (userAccountId.intValue() == updateAccountId) {return;}
        throw new AuthorizeException("update-userid.validation");
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



    private static String getMerthodName(JoinPoint jp) {
        String args = Arrays.asList(jp.getArgs()).stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        return method.getName();
    }
}
