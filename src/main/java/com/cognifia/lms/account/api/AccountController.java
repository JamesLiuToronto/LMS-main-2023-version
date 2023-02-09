package com.cognifia.lms.account.api;

import javax.validation.Valid;

import com.cognifia.lms.account.dto.AccountDTO;
import com.cognifia.lms.account.model.SpecialGroupCodeTypeConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import com.cognifia.lms.account.dto.AccountRegistrationDTO;
import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.model.GroupType;
import com.cognifia.lms.account.model.Person;
import com.cognifia.lms.account.service.AccountService;
import com.cognifia.lms.account.validation.GroupTypeRegisterValidator;
import com.cognifia.lms.account.security.token.AuthorizeUser;
import com.cognifia.lms.common.domain.valueobject.EmailAddress;
import com.cognifia.lms.common.security.LoginUserInfoUtility;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/account")
@Tag(name = "User Account Interface", description = "the API with documentation annotations")
public class AccountController {

    private AccountService service;


    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get UserAccount information",
            description = "This will get user account information by userAccountId ")
    public List<AccountDTO> getUsers() {
        return service.getAccountDTOList();
    }

    @GetMapping(value = "/{userAccountId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get UserAccount information",
               description = "This will get user account information by userAccountId ")
    public AccountDTO getById(@Parameter(description = "userId", required = true) @PathVariable int userAccountId) {
        return service.getAccountDTOById(userAccountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @AuthorizeUser(requiredRoles = {SpecialGroupCodeTypeConstant.USER})
    @PostMapping(value = "/by-admin", consumes = {MediaType.APPLICATION_JSON_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Register new UserAccount By Admin ",
               description = "This service is for registering All type users, authentication required")
    public AccountDTO registerNewAccountByAdmin(@Valid @RequestBody AccountRegistrationDTO dto) {
        GroupTypeRegisterValidator.validateNullGroupType(dto.getGroupTypeList());
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        return service.newAccount(dto.getEmailAddress(), dto.getPassword(), dto.getLoginSourceType(),
                                  dto.getFirstName(), dto.getLastName(), updateUserId, dto.getGroupTypeList());
    }

    @ResponseStatus(HttpStatus.OK)
    @AuthorizeUser(requiredRoles = SpecialGroupCodeTypeConstant.USER)
    @PutMapping(value = "/{userAccountId}/activate", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Activate account")
    public AccountDTO activateUserAccount(
            @Parameter(description = "User Account ID", required = true) @PathVariable int userAccountId) {
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        return service.activateAccount(userAccountId, updateUserId);
    }

    @ResponseStatus(HttpStatus.OK)
    @AuthorizeUser(requiredRoles = SpecialGroupCodeTypeConstant.USER)
    @PutMapping(value = "/{userAccountId}/deactivate", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "deactivate account")
    public AccountDTO deactivateUserAccount(
            @Parameter(description = "User Account ID", required = true) @PathVariable int userAccountId,
            @Parameter(description = "Deactivate Reason", required = true) @RequestParam String reason) {
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        return service.disableAccount(userAccountId, updateUserId, reason);
    }

    @AuthorizeUser(requiredRoles = {SpecialGroupCodeTypeConstant.SELF})
    @PutMapping(value = "/{userAccountId}/personinfo", consumes = {MediaType.APPLICATION_JSON_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update User Information")
    public AccountDTO updateUserInfo(
            @Parameter(description = "User Account ID", required = true) @PathVariable int userAccountId,
            @Parameter(description = "Person information", required = true,
                       schema = @Schema(implementation = Person.class)) @RequestBody Person person) {
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        return service.changePersonInfo(userAccountId, person, updateUserId);
    }

    @AuthorizeUser(requiredRoles = {SpecialGroupCodeTypeConstant.SELF})
    @PutMapping(value = "/{userAccountId}/emailaddress", consumes = {MediaType.APPLICATION_JSON_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update User Email")
    public AccountDTO updateEmailAddress(
            @Parameter(description = "User Account ID", required = true) @PathVariable int userAccountId,
            @Parameter(description = "Email information", required = true,
                       schema = @Schema(implementation = EmailAddress.class)) @RequestBody EmailAddress emailAddress) {
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        return service.changeEmailAddress(userAccountId, emailAddress, updateUserId);
    }

    @AuthorizeUser(requiredRoles = SpecialGroupCodeTypeConstant.USER)
    @PostMapping(value = "/{userAccountId}/usergroup", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Assign group type to user")
    public AccountDTO assignUserGroup(
            @Parameter(description = "User Account ID", required = true) @PathVariable int userAccountId,
            @RequestParam("group-type") String groupType) {
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        return service.AssignUserGroup(userAccountId, GroupType.valueOf(groupType), updateUserId);
    }
}

