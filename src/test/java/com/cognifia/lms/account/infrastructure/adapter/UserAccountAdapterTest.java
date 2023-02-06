package com.cognifia.lms.account.infrastructure.adapter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;

import org.testng.annotations.Test;

import com.cognifia.lms.account.infrastructure.AbstractDatabaseTest;
import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.model.UserStatus;
import com.cognifia.lms.common.domain.valueobject.EmailAddress;

public class UserAccountAdapterTest extends AbstractDatabaseTest {

    @Autowired
    private UserAccountAdapter adapter;

    @Test
    public void test_new_account() {
        Account user = new Account();
        user.setEmailAddress(new EmailAddress("test@test.com"));
        user.setUserStatus(UserStatus.PENDING);

        adapter.persistAccount(user, 0);

        Account account = adapter.getAccountByEmailAddress("test@test.com");
        assertNotNull(account);
        assertEquals(account.getUserStatus(), UserStatus.PENDING);
    }

}
