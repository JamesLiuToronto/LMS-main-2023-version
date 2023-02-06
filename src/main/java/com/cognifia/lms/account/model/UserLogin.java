package com.cognifia.lms.account.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cognifia.lms.common.exception.AppMessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
public class UserLogin {

    private static final int MAX_LOGIN_ATTEMPT = 5;

    private Integer userId;
    private String password;
    private String authenticationSource;
    private boolean locked;
    private LocalDateTime lastSuccessLogin;
    private LocalDateTime lastFailedLogin;
    private int failedLoginAttempt;

    @Builder
    public UserLogin(Integer userId, String password, String authenticationSource) {
        this.userId = userId;
        this.password = password;
        this.authenticationSource = authenticationSource;
    }

    public UserLogin(int userId, String password, LoginSourceType sourceType) {
        this.userId = userId;
        this.password = password;
        authenticationSource = sourceType.name();
        locked = false;
        failedLoginAttempt = 0;
    }

    public void changePassword(String oldPassword, String newPassword, Account updateUserAccount) {
        if (!this.password.equals(oldPassword)) {
            throw new AppMessageException("userAccount.validation.old_password_mismatch");
        }
        changePasswordDetail(newPassword, updateUserAccount);
    }

    public void changePasswordByToken(String newPassword, Account updateUserAccount) {
        changePasswordDetail(newPassword, updateUserAccount);
    }

    private void changePasswordDetail(String newPassword, Account updateUserAccount) {
        if (this.getAuthenticationSource() != null) {
            throw new AppMessageException("userAccount.validation.third_party.password_change");
        }
        validatePasswordByACL(updateUserAccount.getRoleList(), updateUserAccount);
        password = newPassword;
    }

    private void validatePasswordByACL(String roleList, Account updateUserAccount) {
        if (updateUserAccount.getUserId() == this.userId) {
            return;
        }
        if (!(updateUserAccount.getRoleList().contains(GroupType.ADMIN.name()))) {
            throw new AppMessageException("userAccount.validation.need_admin_right");
        }
    }


    public void changeAuthenticationSource(String authenticationSource, Account updateUserAccount) {
        if (!(updateUserAccount.getRoleList().contains(GroupType.ADMIN.name()))) {
            throw new AppMessageException("userAccount.validation.need_admin_right");
        }
        this.authenticationSource = authenticationSource;
    }

    public void registerSuccessLogin() {
        lastSuccessLogin = LocalDateTime.now();
        locked = false;
        failedLoginAttempt = 0;
    }

    public void registerFailedLogin() {
        lastFailedLogin = LocalDateTime.now();
        failedLoginAttempt++;
        if (failedLoginAttempt > MAX_LOGIN_ATTEMPT) {
            locked = true;
        }
    }

    public void UnlockAccount(Account updateUserAccount) {
        if (locked) {
            if (!updateUserAccount.hasAdminRole()) {
                throw new AppMessageException("userAccount.validation.need_admin_right");
            }
            locked = false;
        }
    }

}
