package com.cognifia.lms.account.security.token.utility;

import com.cognifia.lms.account.security.token.model.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author James Liu
 * @date 02/07/2023 -- 8:05 PM
 */
public class UserUtility {

    public static CurrentUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (CurrentUser) auth.getPrincipal();
    }

    public static int getCurrentLoginUserId(){
        CurrentUser cuser = getCurrentUser();
        return cuser.getUserId() ;
    }
}
