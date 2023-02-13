package com.cognifia.lms.account.infrastructure.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cognifia.lms.account.security.token.AuthorizeException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import com.cognifia.lms.account.infrastructure.entity.UserAccountEntity;
import com.cognifia.lms.account.infrastructure.entity.UserGroupEntity;
import com.cognifia.lms.account.infrastructure.mapper.AccountMapper;
import com.cognifia.lms.account.infrastructure.mapper.UserGroupMapper;
import com.cognifia.lms.account.infrastructure.repository.UserAccountRepository;
import com.cognifia.lms.account.infrastructure.repository.UserGroupRepository;
import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.model.UserGroup;
import com.cognifia.lms.common.exception.AppMessageException;

@Component
@AllArgsConstructor
public class UserAccountAdapter {

    private UserAccountRepository userAccountRepository;
    private UserGroupRepository userGroupRepository;

    public List<Account> getAccountList(){
        List<Account> returnList = new ArrayList<>();
        List<UserAccountEntity> list = userAccountRepository.findAll() ;
        list.stream().forEach(x-> returnList.add(AccountMapper.entityToModel(x)));
        return returnList ;

    }
    public Account getAccountById(int id) {
        Optional<UserAccountEntity> entity = userAccountRepository.findById(id);
        if (!entity.isPresent()) {
            throw new AppMessageException("ID_NOT_FOUND");
        }
        return AccountMapper.entityToModel(entity.get());
    }

    public Account getAccountByEmailAddress(String emailAddress) {
        System.out.println("email=" + emailAddress );
        List<UserAccountEntity> entities = userAccountRepository.findByEmailAddress(emailAddress);
        if ((entities == null) || entities.isEmpty()) {
            throw new AppMessageException("userAccount.validation.invalid.emailAddress");
        }
        return AccountMapper.entityToModel(entities.get(0));
    }

    public Account persistAccount(Account account, int updateUserId) {
        UserAccountEntity entity = AccountMapper.INSTANCE.modelToEntity(account, account.getPersonInfo(),
                                                                        account.getEmailAddress().toString());
        entity.setAudit(updateUserId);
        for (UserGroupEntity rec : entity.getUserGroups()) {
            rec.setUserAccountEntity(entity);
        }
        userAccountRepository.saveAndFlush(entity);
        return AccountMapper.entityToModel(entity);
    }

    public void persistNewUserGroup(int userAccountId, UserGroup model, int updateUserId) {
        Optional<UserAccountEntity> optional = userAccountRepository.findById(userAccountId);
        UserAccountEntity account;
        if (optional.isPresent()) {
            account = optional.get();
        }
        else {
            throw new AppMessageException("userAccount.validation.invalid.userid");
        }
        UserGroupEntity entity = UserGroupMapper.INSTANCE.modelToEntity(model);
        entity.setUserAccountEntity(account);
        entity.setAudit(updateUserId);
        account.getUserGroups().add(entity);
        userAccountRepository.saveAndFlush(account);
    }

    public void persistUserGroup(UserGroup model, int updateUserId) {
        UserGroupEntity entity = UserGroupMapper.INSTANCE.modelToEntity(model);
        entity.setAudit(updateUserId);
        userGroupRepository.saveAndFlush(entity);
    }

    public UserGroup getUserGroupById(int id) {
        Optional<UserGroupEntity> optional = userGroupRepository.findById(id);
        UserGroupEntity entity;
        if (optional.isPresent()) {
            entity = optional.get();
        }
        else {
            throw new AppMessageException("userGroup.invalid.id");
        }
        return UserGroupMapper.entityToModel(entity);
    }

    public boolean checkMethodACLByGroup(int userId, String methodName, String groupCode){
        boolean useracl = userAccountRepository.findMethodACLByUserId(userId, methodName) > 0 ? true: false ;
        if (useracl){ return true;} ;
        if (userAccountRepository.findMethodACLByGroupCode("ALL", methodName) > 0 ) {
            return true ;
        }
        if (groupCode == null) {
            return false;
        }

        if (groupCode.equalsIgnoreCase("SELF")){
            return (userAccountRepository.findMethodACLByGroupCode("SELF", methodName) > 0 ?true:false) ;
        }

        if (groupCode.equalsIgnoreCase("FAMILY")){
            return (userAccountRepository.findMethodACLByGroupCode("FAMILY", methodName) > 0 ?true:false) ;
        }
        return false ;
    }


}
