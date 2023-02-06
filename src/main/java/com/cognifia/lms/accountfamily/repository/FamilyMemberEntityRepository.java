package com.cognifia.lms.accountfamily.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognifia.lms.accountfamily.domain.FamilyMemberEntity;

@Repository
public interface FamilyMemberEntityRepository extends JpaRepository<FamilyMemberEntity, Integer> {

    List<FamilyMemberEntity> findByFamilyId(int familyId);
}