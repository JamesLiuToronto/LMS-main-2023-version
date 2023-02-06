package com.cognifia.lms.account.infrastructure.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.cognifia.lms.common.infractructure.InfraEntityBase;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_Useraccount")
public class UserAccountEntity extends InfraEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userId;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "emailaddress")
    private String emailAddress;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "statuscode")
    private String statusCode;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "userAccountEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserGroupEntity> userGroups = new ArrayList<>();

}
