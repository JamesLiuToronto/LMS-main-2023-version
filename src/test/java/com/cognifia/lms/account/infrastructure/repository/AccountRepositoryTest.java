package com.cognifia.lms.account.infrastructure.repository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.testng.annotations.Test;

import com.cognifia.lms.account.infrastructure.AbstractDatabaseTest;
import com.cognifia.lms.account.infrastructure.entity.UserAccountEntity;

public class AccountRepositoryTest extends AbstractDatabaseTest {

    @Autowired
    private UserAccountRepository repository;

    @Test
    public void test_db_operation() {
        UserAccountEntity user = new UserAccountEntity();
        user.setEmailAddress("test@test.com");
        int id = repository.save(user).getUserId();

        Optional<UserAccountEntity> entityOptional = repository.findById(id);
        assertTrue(entityOptional.isPresent());
        assertEquals(entityOptional.get().getEmailAddress(), "test@test.com");

        repository.deleteById(id);
        UserAccountEntity found = repository.findById(id).orElse(null);
        assertNull(found);
    }
}
