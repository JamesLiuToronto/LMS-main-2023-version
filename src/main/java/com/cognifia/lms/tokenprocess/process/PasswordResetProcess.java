package com.cognifia.lms.tokenprocess.process;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cognifia.lms.account.service.AccountService;
import com.cognifia.lms.account.service.LoginService;
import com.cognifia.lms.account.security.token.dto.TokenDTO;
import com.cognifia.lms.common.exception.AppMessageException;

@Component
@Qualifier("passwordResetProcess")
public class PasswordResetProcess extends BaseProcess implements Processor {

    private final LoginService service;

    public PasswordResetProcess(AccountService accountService, LoginService service) {
        super(accountService);
        this.service = service;
    }

    public void runProcess(TokenDTO dto) {
        String[] values = dto.getKeyValue().split(",");
        if (values.length < 3) {
            throw new AppMessageException("process.token.reset-password.invalid.token");
        }
        int userId = Integer.parseInt(values[0]);
        int updateUserId = Integer.parseInt(values[2]);
        String password = values[1];
        validateSelfAdminRole(userId, updateUserId);
        service.changePasswordByToken(userId, password, updateUserId);
    }
}
