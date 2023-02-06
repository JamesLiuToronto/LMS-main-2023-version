package com.cognifia.lms.account.model;

import org.springframework.lang.NonNull;

import com.cognifia.lms.common.domain.base.DomainObjectId;

public class AccountKey extends DomainObjectId {

    public AccountKey(@NonNull String uuid) {
        super(uuid);
    }

}
