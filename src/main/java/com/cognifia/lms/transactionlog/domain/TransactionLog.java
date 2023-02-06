package com.cognifia.lms.transactionlog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="logging_transactionlog")
@NamedQuery(name = "TransactionLog.findByUUID", query = "select u from TransactionLog u where u.uuid = ?1 ")

public class TransactionLog{


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="transactionlogid")
    int transactionLogID ;

    @NotNull
    //@Size(min = 2, message="Street Name is at least 2 characters long!")
    @Column(name="uuid")
    String uuid ;

    @NotNull
    @Column(name="transactiontypecode")
    String transactionTypeCode ;

    @NotNull
    @Column(name="message")
    String message ;


    @Column(name="statuscode")
    String statusCode ;

    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "auditid", referencedColumnName = "auditid")
    @Column(name="userid")
    private int userId;

    @NotNull
    @Column(name="utimestamp")
    LocalDateTime utimestamp ;

}
