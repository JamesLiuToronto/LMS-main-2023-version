package com.cognifia.lms.account.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

import com.cognifia.lms.common.domain.base.BaseEntity;

@Getter
public class UserGroup extends BaseEntity {
    private Integer userGroupId;
    private GroupType groupType;
    private boolean active;

    @Builder
    public UserGroup(String uuid, Integer userGroupId, GroupType groupType, boolean active) {
        super(uuid);
        this.userGroupId = userGroupId;
        this.groupType = groupType;
        this.active = active;
    }

    @Builder
    public UserGroup(GroupType groupType) {
        super(UUID.randomUUID().toString());
        this.groupType = groupType;
        active = true;
    }

    public UserGroup() {
        super(UUID.randomUUID().toString());
    }

    public void AssignNewUserGroup(GroupType groupType) {
        this.groupType = groupType;
        active = true;
    }

    public boolean isAdmin() {
        return GroupType.ADMIN.equals(groupType);
    }

    public void disableGroup() {
        active = false;
    }

    public void activateGroup() {
        active = true;
    }
}
