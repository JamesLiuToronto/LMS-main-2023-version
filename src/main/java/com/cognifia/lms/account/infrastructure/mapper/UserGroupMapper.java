package com.cognifia.lms.account.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.cognifia.lms.account.infrastructure.entity.UserGroupEntity;
import com.cognifia.lms.account.model.GroupType;
import com.cognifia.lms.account.model.UserGroup;

@Mapper
public interface UserGroupMapper {

    UserGroupMapper INSTANCE = Mappers.getMapper(UserGroupMapper.class);

    @Named("entityToModel")
    static UserGroup entityToModel(final UserGroupEntity entity) {

        return UserGroup.builder().uuid(entity.getUuid()).groupType(GroupType.valueOf(entity.getGroupCode()))
                        .active(entity.isActive()).userGroupId(entity.getGroupId()).build();
    }

    @Mapping(source = "model.groupType", target = "groupCode")
    UserGroupEntity modelToEntity(final UserGroup model);
}
