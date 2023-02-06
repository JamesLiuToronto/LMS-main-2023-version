package com.cognifia.lms.account.dto;

import java.io.Serializable;

import com.cognifia.lms.account.model.Account;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginDTO implements Serializable {
    private int userId;
    private String token;
    private AccountDTO acount ;
}
