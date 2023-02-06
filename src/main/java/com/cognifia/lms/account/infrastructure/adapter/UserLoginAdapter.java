package com.cognifia.lms.account.infrastructure.adapter;

import java.util.Optional;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import com.cognifia.lms.account.infrastructure.entity.UserLoginEntity;
import com.cognifia.lms.account.infrastructure.mapper.UserLoginMapper;
import com.cognifia.lms.account.infrastructure.repository.UserLoginRepository;
import com.cognifia.lms.account.model.UserLogin;
import com.cognifia.lms.common.exception.AppMessageException;

@Component
@AllArgsConstructor
public class UserLoginAdapter {

    private UserLoginRepository userLoginRepository;

    public UserLogin getUserLoginById(int userId) {
        Optional<UserLoginEntity> optional = userLoginRepository.findById(userId);
        if (!optional.isPresent()) {
            throw new AppMessageException("ID_NOT_FOUND");
        }
        return UserLoginMapper.INSTANCE.entityToModel(optional.get());
    }

    public UserLogin persistUserLogin(UserLogin model, int updateUserId) {
        UserLoginEntity entity = UserLoginMapper.INSTANCE.modelToEntity(model);
        entity.setAudit(updateUserId);
        userLoginRepository.saveAndFlush(entity);
        return UserLoginMapper.INSTANCE.entityToModel(entity);
    }

}
