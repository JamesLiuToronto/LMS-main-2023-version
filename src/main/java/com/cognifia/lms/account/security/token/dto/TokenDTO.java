package com.cognifia.lms.account.security.token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO implements java.io.Serializable{

    String type;
    String keyName ;
    String keyValue ;
    boolean isExpired ;



}
