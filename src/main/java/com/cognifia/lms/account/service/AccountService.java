package com.cognifia.lms.account.service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import com.cognifia.lms.account.dto.AccountDTO;
import com.cognifia.lms.account.dto.AccountDTOMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import com.cognifia.lms.account.infrastructure.adapter.UserAccountAdapter;
import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.model.GroupType;
import com.cognifia.lms.account.model.LoginSourceType;
import com.cognifia.lms.account.model.Person;
import com.cognifia.lms.account.model.UserGroup;
import com.cognifia.lms.common.annotation.log.LogMethodData;
import com.cognifia.lms.common.domain.valueobject.EmailAddress;
import com.cognifia.lms.common.exception.AppMessageException;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class AccountService {
    private UserAccountAdapter userAccountAdapter;
    private LoginService loginService;


    public List<AccountDTO> getAccountDTOList() {
        List<Account> list = userAccountAdapter.getAccountList();
        List<AccountDTO> resultList = new ArrayList<>() ;
        list.stream().forEach(x->
                resultList.add(AccountDTOMapper.INSTANCE.modelToDto(x, x.getPersonInfo(), x.getEmailAddress().getEmail())));
        return resultList ;

    }
    public Account getAccountById(int id) {
        return userAccountAdapter.getAccountById(id);
    }

    public AccountDTO getAccountDTOById(int id) {
        Account x = userAccountAdapter.getAccountById(id);
        return AccountDTOMapper.INSTANCE.modelToDto(x, x.getPersonInfo(), x.getEmailAddress().getEmail()) ;
    }

    @LogMethodData
    public AccountDTO newAccount(String emailAddress, String password, String loginSourceType, String firstName,
                              String lastName, int updateUserId, List<GroupType> groupTypeList) {
        Account request = new Account();
        request.setNewAccount(emailAddress, firstName, lastName, groupTypeList);
        Account account = userAccountAdapter.persistAccount(request, updateUserId);
        loginService.registerNewLogin(account.getUserId(), password,
                                      StringUtils.isBlank(loginSourceType) ? LoginSourceType.NULL : LoginSourceType.valueOf(loginSourceType),
                                      updateUserId);
        return getAccountDTOById(account.getUserId());
    }

    private Account registerNewUserGroup(int userId, GroupType type, int updateUserId) {
        adminValidator(updateUserId);
        Account account = userAccountAdapter.getAccountById(userId);
        UserGroup model = account.assignUserGroup(type);
        userAccountAdapter.persistNewUserGroup(userId, model, updateUserId);
        return userAccountAdapter.getAccountById(userId);
    }

    @LogMethodData
    public AccountDTO changePersonInfo(int id, Person person, int updateUserId) {
        adminOrSelfValidator(id, updateUserId);
        Account account = userAccountAdapter.getAccountById(id);
        account.changePersonInfo(person);
        userAccountAdapter.persistAccount(account, updateUserId);
        return getAccountDTOById(id);
    }

    @LogMethodData
    public AccountDTO changeEmailAddress(int id, EmailAddress emailAddress, int updateUserId) {
        adminOrSelfValidator(id, updateUserId);
        Account account = userAccountAdapter.getAccountById(id);
        account.changeEmailAddress(emailAddress);
        userAccountAdapter.persistAccount(account, updateUserId);
        return getAccountDTOById(id);
    }

    @LogMethodData
    public AccountDTO activateAccount(int userId, int updateUserId) {
        adminOrSelfValidator(userId, updateUserId);
        Account account = userAccountAdapter.getAccountById(userId);
        account.activateAccount();
        userAccountAdapter.persistAccount(account, updateUserId);
        return getAccountDTOById(userId);
    }

    @LogMethodData
    public AccountDTO disableAccount(int id, int updateUserId, String reason) {
        adminValidator(updateUserId);
        Account account = userAccountAdapter.getAccountById(id);
        account.disAbleAccount(reason);
        userAccountAdapter.persistAccount(account, updateUserId);
        return getAccountDTOById(id);
    }

    @LogMethodData
    public AccountDTO AssignUserGroup(int userId, GroupType type, int updateUserId) {
        adminValidator(updateUserId);
        Account account = userAccountAdapter.getAccountById(userId);
        if (account.isDisable()) {
            adminValidator(updateUserId);
        }
        UserGroup model = account.assignUserGroup(type);
        userAccountAdapter.persistNewUserGroup(userId, model, updateUserId);
        return getAccountDTOById(userId);
    }

    @LogMethodData
    public Account disableUserGroup(int id, int updateUserId) {
        adminValidator(updateUserId);
        UserGroup model = userAccountAdapter.getUserGroupById(id);
        model.disableGroup();
        userAccountAdapter.persistUserGroup(model, updateUserId);
        return userAccountAdapter.getAccountById(id);
    }

    private String getMessage(int id, String message) {
        return "id=" + id + ", " + message;
    }

    private void adminOrSelfValidator(int userId, int updateUserId) {
        if (!isAdminRole(updateUserId) && (userId == updateUserId)) {
            throw new AppMessageException("userAccount.validation.admin.role.or.self.error");
        }
    }

    private void adminValidator(int updateUserId) {
        if (!isAdminRole(updateUserId)) {
            throw new AppMessageException("userAccount.validation.admin.role.error");
        }
    }

    private boolean isAdminRole(int userId) {
        return (userId == -1) ? true : getAccountById(userId).hasAdminRole();
    }
}
