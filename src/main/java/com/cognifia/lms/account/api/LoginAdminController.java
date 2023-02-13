package com.cognifia.lms.account.api;

import com.cognifia.lms.account.dto.LoginDTO;
import com.cognifia.lms.account.model.SpecialGroupCodeTypeConstant;
import com.cognifia.lms.account.service.LoginService;
import com.cognifia.lms.account.security.token.AuthorizeUser;
import com.cognifia.lms.common.dto.ResultStatus;
import com.cognifia.lms.common.dto.SimpleResultDTO;
import com.cognifia.lms.common.security.LoginUserInfoUtility;
import com.cognifia.lms.tokenprocess.TokenServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/login")
@Tag(name = "User login Interface", description = "the API with login ")
public class LoginAdminController {

    private LoginService service;
    private TokenServicePort tokenServicePort;
    private PasswordEncoder passwordEncoder;


    @GetMapping(value = "login-user-info",
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login Success information", description = "login success ")
    public LoginDTO getLoginUserInfo() {
        return service.getLoginUserInfo();
    }

    @AuthorizeUser(requiredRoles = {SpecialGroupCodeTypeConstant.SELF})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/change-password",
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login information", description = "This will generate token String by login ")
    public void changePassword(
            @Parameter(description = "userAccountId", required = true) @RequestParam("userAccountId") int userAccountId,
            @Parameter(description = "oldPassword", required = true) @RequestParam("old-password") String oldPassword,
            @Parameter(description = "new-password", required = true) @RequestParam("newPassword") String newPassword) {
        String hashedOldPassword = passwordEncoder.encode(oldPassword);
        String hashedNewPassword = passwordEncoder.encode(newPassword);
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        service.changePassword(userAccountId, hashedOldPassword, hashedNewPassword, updateUserId);
    }

    @AuthorizeUser(requiredRoles = SpecialGroupCodeTypeConstant.USER)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/change-login-source",
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login information", description = "This will generate token String by login ")
    public void changeLoginSource(@Parameter(description = "userAccountId", required = true) @RequestParam(
            "uuserAccountId") int userAccountId, @Parameter(description = "LoginSource", required = true) @RequestParam(
            "login-source-type") String loginSourceType) {
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        service.changeLoginSource(userAccountId, loginSourceType, updateUserId);
    }

    @AuthorizeUser(requiredRoles = SpecialGroupCodeTypeConstant.USER)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/unlock_user",
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login information", description = "This will generate token String by login ")
    public void unlockUser(@Parameter(description = "userAccountId", required = true) @RequestParam(
            "userAccountId") int userAccountId) {
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        service.unlockUser(userAccountId, updateUserId);
    }

    @AuthorizeUser(requiredRoles = {SpecialGroupCodeTypeConstant.SELF})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/reset-password-token",
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Reset Password ",
               description = "the API for testing purpose, it should be invisible to user in production, reset password token")
    public String changePassword(
            @Parameter(description = "userAccountId", required = true) @RequestParam("userAccountId") int userAccountId,
            @Parameter(description = "new-password", required = true) @RequestParam("newPassword") String newPassword) {
        String hashedNewPassword = passwordEncoder.encode(newPassword);
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        return tokenServicePort.getResetPasswordToken(userAccountId, hashedNewPassword, updateUserId);
    }

    @AuthorizeUser(requiredRoles = {SpecialGroupCodeTypeConstant.SELF})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/activate-token/{userAccountId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "the API for testing purpose, it should be invisible to user in production, with generate activate token",
            description = "This service is the API with token process general")
    public SimpleResultDTO getActivateToken(
            @Parameter(description = "userAccountId", required = true) @PathVariable("userAccountId") Integer userAccountId) {
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        String token = tokenServicePort.getActivateAccountToken(userAccountId, updateUserId);
        return SimpleResultDTO.builder()
                .status(ResultStatus.SUCCESS.name())
                .note(token)
                .build();
    }
}

