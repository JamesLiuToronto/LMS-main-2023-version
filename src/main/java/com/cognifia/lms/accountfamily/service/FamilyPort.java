package com.cognifia.lms.accountfamily.service;

import java.util.List;

import com.cognifia.lms.accountfamily.dto.FamilyDTO;

public interface FamilyPort {

    List<FamilyDTO> getFamilyAccountList(int userId);

    String setFamilyMember(int askedForUserId, int requestedByUserId);

    void activateFamilyMember(int askedForUserId, int requestedByUserId);
}
