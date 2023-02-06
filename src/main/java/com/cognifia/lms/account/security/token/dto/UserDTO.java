package com.cognifia.lms.account.security.token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements java.io.Serializable{

    String uuid;
    int userID;
    String emailAddress;
    String firstName;
    String lastName;
    String roleList;
    String methodList ;
    String disallowMethodList ;
    String allowMethodList ;
    boolean isExpired ;

    public boolean isAllowed(String methodCode){

        if (!checkAcl(methodCode))
            return false ;

        String[] roles = methodCode.split(",") ;
        if (roles.length == 0)
            return false ;
        String root = roles[0] + ".ALL" ;
        return checkAcl(root);
    }

    private boolean checkAcl(String methodCode){
        if (allowMethodList.contains(methodCode))
            return true ;
        if (disallowMethodList.contains(methodCode))
            return false ;
        return true ;

    }



}
