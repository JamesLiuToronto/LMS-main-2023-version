package com.cognifia.lms.account.infrastructure.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.cognifia.lms.account.infrastructure.entity.UserAccountEntity;
import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.model.Person;
import com.cognifia.lms.account.model.UserGroup;
import com.cognifia.lms.account.model.UserStatus;
import com.cognifia.lms.common.domain.valueobject.EmailAddress;

@Mapper(uses = {UserGroupMapper.class})
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Named("entityToModel")
    static Account entityToModel(final UserAccountEntity entity) {
        Person person = Person.builder().firstName(entity.getFirstName()).lastName(entity.getLastName()).build();
        List<UserGroup> list = new ArrayList<>();
        entity.getUserGroups().forEach(x -> list.add(UserGroupMapper.entityToModel(x)));
        return new Account(entity.getUuid(), entity.getUserId(), new EmailAddress(entity.getEmailAddress()), person,
                           UserStatus.valueOf(entity.getStatusCode()), list, entity.getNote());
    }

    @Mapping(source = "emailAddress", target = "emailAddress")
    @Mapping(source = "person.firstName", target = "firstName")
    @Mapping(source = "person.lastName", target = "lastName")
    @Mapping(source = "model.userStatus", target = "statusCode")
    @Mapping(source = "model.userGroups", target = "userGroups")
    @Mapping(source = "model.note", target = "note")
    UserAccountEntity modelToEntity(final Account model, Person person, String emailAddress);
}



