package com.cognifia.lms.account.dto;

import com.cognifia.lms.account.model.GroupType;
import com.cognifia.lms.account.model.Person;
import com.cognifia.lms.account.model.UserGroup;
import com.cognifia.lms.account.model.UserStatus;
import com.cognifia.lms.common.domain.base.BaseEntity;
import com.cognifia.lms.common.domain.valueobject.EmailAddress;
import com.cognifia.lms.common.exception.AppMessageException;
import com.cognifia.lms.common.exception.AppNotNullException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO extends BaseEntity {

    private int userId;

    private String emailAddress;
    private String firstName;

    private String lastName ;
    private String userStatus;

    private String roleList ;

    private String note;

}
