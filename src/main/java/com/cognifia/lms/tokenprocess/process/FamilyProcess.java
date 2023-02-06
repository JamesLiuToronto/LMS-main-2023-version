package com.cognifia.lms.tokenprocess.process;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cognifia.lms.account.service.AccountService;
import com.cognifia.lms.accountfamily.service.FamilyPort;
import com.cognifia.lms.account.security.token.dto.TokenDTO;
import com.cognifia.lms.common.exception.AppMessageException;

@Component
@Qualifier("familyProcess")
public class FamilyProcess extends BaseProcess implements Processor {

    private FamilyPort service;

    public FamilyProcess(AccountService accountService, FamilyPort service) {
        super(accountService);
        this.service = service;
    }

    public void runProcess(TokenDTO dto) {
        String[] values = dto.getKeyValue().split(",");
        if (values.length < 2) {
            throw new AppMessageException("process.token.activate-user.invalid.token");
        }
        int askedForUserId = Integer.parseInt(values[0]);
        int requestedByUserId = Integer.parseInt(values[1]);
        service.activateFamilyMember(askedForUserId, requestedByUserId);
    }
}
