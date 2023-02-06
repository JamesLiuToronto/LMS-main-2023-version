package com.cognifia.lms.accountfamily.api;

import java.util.List;

import com.cognifia.lms.account.model.SpecialGroupCodeTypeConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import com.cognifia.lms.accountfamily.dto.FamilyDTO;
import com.cognifia.lms.accountfamily.service.FamilyPort;
import com.cognifia.lms.account.security.token.AuthorizeUser;
import com.cognifia.lms.common.security.LoginUserInfoUtility;

@RestController
@AllArgsConstructor
@RequestMapping("api/account-family")
@Tag(name = "This is Account family interface", description = "the API with family interface")
public class AccountFamilyController {

    private FamilyPort service;

    @AuthorizeUser(requiredRoles = {SpecialGroupCodeTypeConstant.SELF})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{userAccountId}")
    @Operation(summary = "the API with token process general",
               description = "This service is the API with token process general")
    public List<FamilyDTO> tokenProcess(@Parameter(description = "userAccountId", required = true) @PathVariable(
            "userAccountId") int userAccountId) {
        return service.getFamilyAccountList(userAccountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/token")
    @Operation(summary = "the API with generate activate token",
               description = "This service is the API with token process general")
    public String setFamily(
            @Parameter(description = "userId", required = true) @RequestParam("aksed-userid") Integer askedForUserId) {
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        return service.setFamilyMember(askedForUserId, updateUserId);
    }
}

