package com.cognifia.lms.account.dto;

import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.model.Person;
import com.cognifia.lms.account.model.UserGroup;
import com.cognifia.lms.account.model.UserStatus;
import com.cognifia.lms.common.domain.valueobject.EmailAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AccountDTOMapper {

    AccountDTOMapper INSTANCE = Mappers.getMapper(AccountDTOMapper.class);

    @Named("dtoToModel")
    static Account dtoToModel(final AccountDTO dto) {
        Person person = Person.builder().firstName(dto.getFirstName()).lastName(dto.getLastName()).build();
        List<UserGroup> list = new ArrayList<>();
        return new Account(dto.getUuid(), dto.getUserId(), new EmailAddress(dto.getEmailAddress()), person,
                           UserStatus.valueOf(dto.getUserStatus()), list, dto.getNote());
    }

    @Mapping(source = "emailAddress", target = "emailAddress")
    @Mapping(source = "person.firstName", target = "firstName")
    @Mapping(source = "person.lastName", target = "lastName")
    @Mapping(source = "model.userStatus", target = "userStatus")
    @Mapping(source = "model.note", target = "note")
    AccountDTO modelToDto(final Account model, Person person, String emailAddress);
}



