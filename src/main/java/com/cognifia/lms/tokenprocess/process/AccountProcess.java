package com.cognifia.lms.tokenprocess.process;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cognifia.lms.account.service.AccountService;
import com.cognifia.lms.account.security.token.dto.TokenDTO;
import com.cognifia.lms.common.exception.AppMessageException;

@Component
@Qualifier("accountProcess")
public class AccountProcess extends BaseProcess implements Processor {

    public AccountProcess(AccountService accountService) {
        super(accountService);
    }

    public void runProcess(TokenDTO dto) {
        String[] values = dto.getKeyValue().split(",");
        if (values.length < 2) {
            throw new AppMessageException("process.token.activate-user.invalid.token");
        }
        int userId = Integer.parseInt(values[0]);
        int updateUserId = Integer.parseInt(values[1]);
        validateSelfAdminRole(userId, updateUserId);
        accountService.activateAccount(userId, updateUserId);
    }
}
