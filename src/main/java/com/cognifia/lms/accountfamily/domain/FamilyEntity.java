package com.cognifia.lms.accountfamily.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "account_Family")
public class FamilyEntity extends InfraEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "familyid")
    private int familyId;

    @Column(name = "familyname")
    private String familyName;

    @Column(name = "note")
    private String note;
}
