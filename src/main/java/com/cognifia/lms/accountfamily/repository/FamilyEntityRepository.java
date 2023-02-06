package com.cognifia.lms.accountfamily.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognifia.lms.accountfamily.domain.FamilyEntity;

@Repository
public interface FamilyEntityRepository extends JpaRepository<FamilyEntity, Integer> {

}