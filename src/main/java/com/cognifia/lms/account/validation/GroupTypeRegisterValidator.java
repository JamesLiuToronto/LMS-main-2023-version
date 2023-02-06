package com.cognifia.lms.account.validation;

import java.util.List;

import com.cognifia.lms.account.model.GroupType;
import com.cognifia.lms.common.exception.AppMessageException;

public class GroupTypeRegisterValidator {

    public static void validateStudentGroupType(List<GroupType> list) {
        validateNullGroupType(list);
        if (list.stream().noneMatch(x -> (GroupType.STUDENT.equals(x) || GroupType.PARENT.equals(x)))) {
            throw new AppMessageException("userAccount.validation.register.student_group_type");
        }
    }

    public static void validateNullGroupType(List<GroupType> list) {
        if ((list == null) || list.isEmpty()) {
            throw new AppMessageException("userAccount.validation.register.notnull_group_type");
        }
    }
}
