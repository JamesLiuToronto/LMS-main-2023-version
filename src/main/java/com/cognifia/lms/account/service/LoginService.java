package com.cognifia.lms.account.service;

import javax.transaction.Transactional;

import com.cognifia.lms.account.dto.AccountDTO;
import com.cognifia.lms.account.dto.AccountDTOMapper;
import com.cognifia.lms.account.infrastructure.repository.UserAccountRepository;
import com.cognifia.lms.common.security.LoginUserInfoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import com.cognifia.lms.account.dto.LoginDTO;
import com.cognifia.lms.account.infrastructure.adapter.UserAccountAdapter;
import com.cognifia.lms.account.infrastructure.adapter.UserLoginAdapter;
import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.model.LoginSourceType;
import com.cognifia.lms.account.model.UserLogin;
import com.cognifia.lms.common.annotation.log.LogMethodData;
import com.cognifia.lms.account.security.token.dto.UserDTO;
import com.cognifia.lms.account.security.token.utility.JWTUtility;
import com.cognifia.lms.common.exception.AppMessageException;

@Service
@AllArgsConstructor
@Transactional
public class LoginService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserAccountAdapter userAccountAdapter;
    private UserLoginAdapter loginAdapter;
    private JWTUtility jwtUtility;
    @Autowired
    private UserAccountRepository userAccountRepository;

    public UserLogin registerNewLogin(int userId, String password, LoginSourceType sourceType, int updateUserId) {
        UserLogin model = new UserLogin(userId, password, sourceType);
        return loginAdapter.persistUserLogin(model, updateUserId);
    }


    public LoginDTO getLoginUserInfo(){
        int updateUserId = LoginUserInfoUtility.getLoginUserId();
        Account account = userAccountAdapter.getAccountById(updateUserId) ;
        return getLoginDTO(account);
    }

    @LogMethodData
    public LoginDTO login(String emailAddress, String password) {
        Account account = userAccountAdapter.getAccountByEmailAddress(emailAddress);
        UserLogin login = loginAdapter.getUserLoginById(account.getUserId());
        if (!validatePassword(login, password)) {
            throw new AppMessageException("userlogin.validation.password_mismatch");
        }
        return getLoginDTO(account);
    }

    private boolean validatePassword(UserLogin login, String password) {
        if (LoginSourceType.valueOf(login.getAuthenticationSource()) !=
                LoginSourceType.NULL)  //means login from third party
        {
            return true;
        }
        return (!login.getPassword().isEmpty()) && (
                passwordEncoder.matches(password, login.getPassword()));
    }

    public LoginDTO getLoginDTO(Account account) {
        UserDTO userDTO = UserDTOMapper.modelToDto(account);
        AccountDTO dto = AccountDTOMapper.INSTANCE.modelToDto(account, account.getPersonInfo(),
                account.getEmailAddress().getEmail());
        return LoginDTO.builder().userId(account.getUserId()).
                token(jwtUtility.createJwtSignedHMAC(userDTO))
                .acount(dto)
                .build();
    }

    @LogMethodData
    public void changePassword(int userId, String oldPassword, String newPassword, int updateUserId) {
        UserLogin login = loginAdapter.getUserLoginById(userId);
        Account updateUserAccount = userAccountAdapter.getAccountById(updateUserId);
        login.changePassword(oldPassword, newPassword, updateUserAccount);
        loginAdapter.persistUserLogin(login, updateUserId);
    }

    @LogMethodData
    public void changePasswordByToken(int userId, String newPassword, int updateUserId) {
        UserLogin login = loginAdapter.getUserLoginById(userId);
        Account updateUserAccount = userAccountAdapter.getAccountById(updateUserId);
        login.changePasswordByToken(newPassword, updateUserAccount);
        loginAdapter.persistUserLogin(login, updateUserId);
    }

    private String getLoginMessage(String emailAddress, int updateUserId) {
        return "emailAddress=" + emailAddress + ", by UserID" + updateUserId;
    }

    @LogMethodData
    public void changeLoginSource(int userId, String sourceType, int updateUserId) {
        UserLogin login = loginAdapter.getUserLoginById(userId);
        Account updateUserAccount = userAccountAdapter.getAccountById(updateUserId);
        login.changeAuthenticationSource(sourceType, updateUserAccount);
        loginAdapter.persistUserLogin(login, updateUserId);
    }

    @LogMethodData
    public void unlockUser(int userId, int updateUserId) {
        UserLogin login = loginAdapter.getUserLoginById(userId);
        Account updateUserAccount = userAccountAdapter.getAccountById(updateUserId);
        login.UnlockAccount(updateUserAccount);
        loginAdapter.persistUserLogin(login, updateUserId);
    }
}
