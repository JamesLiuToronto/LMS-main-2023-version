package com.cognifia.lms.account.service;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import com.cognifia.lms.account.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.testng.annotations.Test;

import com.cognifia.lms.account.infrastructure.AbstractDatabaseTest;
import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.model.GroupType;
import com.cognifia.lms.account.model.UserStatus;

public class AccountServiceIT extends AbstractDatabaseTest {
    @Autowired
    private AccountService service;

    @Test
    public void create_new_account() {
        AccountDTO account = service.newAccount("test@test.com", "pwd", null, "first",
                                             "last", 0, List.of(GroupType.STUDENT));
        assertNotNull(account);
        assertEquals(account.getUserStatus(), UserStatus.PENDING);
    }
}
