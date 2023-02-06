package com.cognifia.lms.account.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

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
@Table(name = "account_UserLogin")
public class UserLoginEntity extends InfraEntityBase {

    @Id
    @Column(name = "userid")
    private int userId;

    @ToString.Exclude
    @Column(name = "password")
    private String password;

    @Column(name = "authenticationsource")
    private String authenticationSource;

    @Column(name = "locked")
    private boolean locked;

    @Column(name = "lastsuccesslogin")
    private LocalDateTime lastSuccessLogin;

    @Column(name = "lastfaliedlogin")
    private LocalDateTime lastFaliedLogin;

    @Column(name = "faliedloginattemp")
    private int faliedLoginAttemp;

}
