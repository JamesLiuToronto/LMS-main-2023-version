package com.cognifia.lms.account.api;

import javax.validation.Valid;

import com.cognifia.lms.account.dto.AccountDTO;
import com.cognifia.lms.common.annotation.log.LogMethodData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import com.cognifia.lms.account.dto.AccountRegistrationDTO;
import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.service.AccountService;
import com.cognifia.lms.account.validation.GroupTypeRegisterValidator;

@RestController
@AllArgsConstructor
@RequestMapping("account/registration")
@Tag(name = "User registration Interface", description = "the API with registration")
public class RegisterController {

    private AccountService service;
    private PasswordEncoder passwordEncoder;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Register new UserAccount for ",
               description = "This service is for registering students and parents, no authentication required")
    public AccountDTO registerNewAccount(@Valid @RequestBody AccountRegistrationDTO dto) {
        GroupTypeRegisterValidator.validateStudentGroupType(dto.getGroupTypeList());
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        return service.newAccount(dto.getEmailAddress(), hashedPassword, dto.getLoginSourceType(), dto.getFirstName(),
                                  dto.getLastName(), -1, dto.getGroupTypeList());
    }

}

