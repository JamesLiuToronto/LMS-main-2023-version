package com.cognifia.lms.accountfamily.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cognifia.lms.common.infractructure.InfraEntityBase;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_FamilyMember")
@NamedQuery(name = "FamilyMemberEntity.findByFamilyId",
            query = "select u from FamilyMemberEntity u where u.familyId = ?1")
public class FamilyMemberEntity extends InfraEntityBase {

    @Id
    @Column(name = "userId")
    private int userId;

    @Column(name = "active")
    private boolean active;

    @Column(name = "familyId")
    private int familyId;
}
