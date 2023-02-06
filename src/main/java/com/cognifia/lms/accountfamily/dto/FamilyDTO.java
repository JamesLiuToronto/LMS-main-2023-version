package com.cognifia.lms.accountfamily.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Value;

import com.cognifia.lms.account.model.Account;

@Value
@Builder
public class FamilyDTO implements Serializable {
    Account userAccount;
    boolean active;
}
