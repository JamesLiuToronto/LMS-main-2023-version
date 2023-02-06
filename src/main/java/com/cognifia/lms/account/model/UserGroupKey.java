package com.cognifia.lms.account.model;

import org.springframework.lang.NonNull;

import com.cognifia.lms.common.domain.base.DomainObjectId;

public class UserGroupKey extends DomainObjectId {

    public UserGroupKey(@NonNull String uuid) {
        super(uuid);
    }

}