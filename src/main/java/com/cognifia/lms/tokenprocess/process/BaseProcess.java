package com.cognifia.lms.tokenprocess.process;

import lombok.AllArgsConstructor;

import com.cognifia.lms.account.service.AccountService;
import com.cognifia.lms.common.exception.AppMessageException;

@AllArgsConstructor
public class BaseProcess {

    AccountService accountService;

    protected void validateSelfAdminRole(int userId, int updateUserId) {
        if (userId != updateUserId) {
            validateAdminRole(updateUserId);
        }
    }

    protected void validateAdminRole(int userId) {
        if (!accountService.getAccountById(userId).hasAdminRole()) {
            throw new AppMessageException("process.token.admin-role.invalid.token");
        }
    }
}
