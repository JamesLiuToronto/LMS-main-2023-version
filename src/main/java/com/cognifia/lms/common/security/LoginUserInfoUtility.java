package com.cognifia.lms.common.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;
import com.cognifia.lms.account.security.token.model.CurrentUser;

import java.util.List;

@Slf4j
public class LoginUserInfoUtility {

    public static int getLoginUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((CurrentUser) principal).getUserId();
        }
        else {
            return 0;
        }
    }


    public static String getLoginUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
           return ((CurrentUser) principal).getUsername();
        }
        else {
            return principal == null ? null : principal.toString();
        }
    }
}
