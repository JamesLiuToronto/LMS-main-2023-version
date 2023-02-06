package com.cognifia.lms.account.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.cognifia.lms.account.infrastructure.entity.UserLoginEntity;
import com.cognifia.lms.account.model.UserLogin;

@Mapper
public interface UserLoginMapper {

    UserLoginMapper INSTANCE = Mappers.getMapper(UserLoginMapper.class);

    @Named("entityToModel")
    UserLogin entityToModel(final UserLoginEntity entity);

    UserLoginEntity modelToEntity(final UserLogin model);
}
