package com.cognifia.lms.account.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.cognifia.lms.common.domain.base.BaseEntity;
import com.cognifia.lms.common.domain.valueobject.EmailAddress;
import com.cognifia.lms.common.exception.AppMessageException;
import com.cognifia.lms.common.exception.AppNotNullException;

@Data
@NoArgsConstructor
public class Account extends BaseEntity {
    private int userId;
    private EmailAddress emailAddress;
    private Person personInfo;
    private UserStatus userStatus;
    private List<UserGroup> userGroups = new ArrayList<>();
    private String note;

    public Account(String uuid, Integer userId, EmailAddress emailAddress, Person personInfo, UserStatus userStatus,
                   List<UserGroup> userGroups, String note) {
        super(uuid);
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.personInfo = personInfo;
        this.userStatus = userStatus;
        this.userGroups = userGroups;
        this.note = note;
    }

    public void setNewAccount(String emailAddress, String firstName, String lastName, List<GroupType> groupTypeList) {
        setUuid(UUID.randomUUID().toString());
        personInfo = new Person(firstName, lastName);

        this.emailAddress = new EmailAddress(emailAddress);
        userStatus = UserStatus.PENDING;
        groupTypeList.forEach(this::assignUserGroup);
    }

    public void changePersonInfo(Person person) {
        personInfo = person;
    }

    public void changeEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public UserGroup assignUserGroup(GroupType type) {
        if (isDisable()) {
            throw new AppMessageException("Account_DISABLED");
        }
        UserGroup find = getUserGroup(type);
        if (find != null) {
            find.activateGroup();
            return find;
        }
        UserGroup ug = new UserGroup();
        ug.AssignNewUserGroup(type);
        userGroups.add(ug);
        return ug;
    }

    public UserGroup disableUserGroup(UserGroup group) {
        UserGroup find = getUserGroup(group.getGroupType());
        if (find == null) {
            throw new AppNotNullException("USERGROUP_NOTFOUND");
        }
        if (!find.isActive()) {
            throw new AppNotNullException("USERGROUP_INACTIVE");
        }
        find.disableGroup();
        return find;
    }

    private UserGroup getUserGroup(GroupType type) {
        return userGroups.stream().filter(r -> r.getGroupType().equals(type)).findAny().orElse(null);
    }

    public void activateAccount() {
        userStatus = UserStatus.ACTIVE;
    }

    public void disAbleAccount(String reason) {
        userStatus = UserStatus.DISABLE;
        note = "Disable Reason: " + reason;
    }

    public boolean isActive() {
        return UserStatus.ACTIVE.equals(userStatus) && ((userGroups != null) && !userGroups.isEmpty());
    }

    public boolean isDisable() {
        return UserStatus.DISABLE.equals(userStatus);
    }

    public boolean isPending() {
        return UserStatus.PENDING.equals(userStatus);
    }

    public String getRoleList() {
        return userGroups.stream().filter(UserGroup::isActive).map(a -> a.getGroupType().toString())
                         .collect(Collectors.joining(","));
    }

    public boolean hasAdminRole() {
        return userGroups.stream().parallel().anyMatch(UserGroup::isAdmin);
    }
}
