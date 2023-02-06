package com.cognifia.lms.account.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cognifia.lms.account.model.GroupType;
import com.cognifia.lms.account.validation.LoginSourceTypeValidation;
import com.cognifia.lms.common.annotation.validation.validation.EmailValidation;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegistrationDTO implements Serializable {

    @EmailValidation
    @NotNull(message = "validation.email.code")
    private String emailAddress;
    private String password;

    @NotNull(message = "login.validation.invalid_data.login_source_type")
    @LoginSourceTypeValidation
    private String loginSourceType;

    private String firstName;

    @NotNull(message = "login.validation.notnull.lastname")
    private String lastName;

    private List<GroupType> groupTypeList;
}
