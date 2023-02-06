package com.cognifia.lms.account.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import com.cognifia.lms.account.dto.AccountDTO;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cognifia.lms.account.infrastructure.AbstractDatabaseTest;
import com.cognifia.lms.account.infrastructure.adapter.UserAccountAdapter;
import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.model.GroupType;
import com.cognifia.lms.account.model.LoginSourceType;
import com.cognifia.lms.account.model.UserStatus;
import com.cognifia.lms.common.domain.valueobject.EmailAddress;

public class AccountServiceTest extends AbstractDatabaseTest {
    private final UserAccountAdapter userAccountAdapter = mock(UserAccountAdapter.class);
    private final LoginService loginService = mock(LoginService.class);
    @InjectMocks
    private AccountService service;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeMethod
    public void setup() {
        reset(userAccountAdapter);
        reset(loginService);
    }

    @Test
    public void create_new_account() {
        Account user = new Account();
        user.setEmailAddress(new EmailAddress("test@test.com"));
        user.setUserStatus(UserStatus.PENDING);

        when(userAccountAdapter.persistAccount(any(Account.class), anyInt())).thenReturn(user);
        when(userAccountAdapter.getAccountById(anyInt())).thenReturn(user);
        when(loginService.registerNewLogin(anyInt(), anyString(), any(LoginSourceType.class), anyInt())).thenReturn(null);

        AccountDTO account = service.newAccount("test@test.com", "pwd", null, "first",
                                             "last", 0, List.of(GroupType.STUDENT));
        assertNotNull(account);
        assertEquals(account.getUserStatus(), UserStatus.PENDING);
    }
}
