package com.cognifia.lms.account.service;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.security.token.dto.UserDTO;

@Mapper
public interface UserDTOMapper {

    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);

    @Named("modelToDto")
    static UserDTO modelToDto(final Account model) {
        return UserDTO.builder().firstName(model.getPersonInfo().getFirstName())
                      .lastName(model.getPersonInfo().getLastName()).emailAddress(model.getEmailAddress().getEmail())
                      .userID(model.getUserId()).uuid(model.getUuid()).roleList(model.getRoleList()).build();
    }
}



