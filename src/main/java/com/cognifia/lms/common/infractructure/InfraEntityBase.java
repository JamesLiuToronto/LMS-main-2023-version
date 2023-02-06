package com.cognifia.lms.common.infractructure;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class InfraEntityBase {

    @Column(name = "createUserId")
    private int createdBy;

    @Column(name = "createDate")
    private LocalDateTime createdOn;

    @Column(name = "updateUserId")
    private int updatedBy;

    @Column(name = "updateDate")
    private LocalDateTime updatedOn;

    public void setAudit(int userId) {
        if (createdBy == 0) {
            createdBy = userId;
            createdOn = LocalDateTime.now();
        }
        updatedBy = userId;
        updatedOn = LocalDateTime.now();
    }
}
