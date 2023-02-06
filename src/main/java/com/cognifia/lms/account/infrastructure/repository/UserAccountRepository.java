package com.cognifia.lms.account.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognifia.lms.account.infrastructure.entity.UserAccountEntity;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Integer> {

    List<UserAccountEntity> findByEmailAddress(String emailAddress);

    List<UserAccountEntity> findByUuid(String uuid);

    @Query(value = "SELECT count(map.methodName) " +
            " From account_group_method_map map, account_usergroup ugroup\n" +
            " WHERE ugroup.userId = :userId \n" +
            " AND (map.groupCode = ugroup.groupCode)\n" +
            " AND map.methodName = :methodName ", nativeQuery=true)
    Integer findMethodACLByUserId(@Param("userId") int userId, @Param("methodName") String methodName);

    @Query(value = "SELECT count(map.methodName) " +
            " From account_group_method_map map" +
            " WHERE map.groupCode = :groupCode" +
            " AND map.methodName = :methodName ", nativeQuery=true)
    Integer findMethodACLByGroupCode(@Param("groupCode") String groupCode, @Param("methodName") String methodName);

}