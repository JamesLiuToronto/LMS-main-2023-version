package com.cognifia.lms.accountfamily.service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import com.cognifia.lms.account.model.Account;
import com.cognifia.lms.account.service.AccountService;
import com.cognifia.lms.accountfamily.domain.FamilyEntity;
import com.cognifia.lms.accountfamily.domain.FamilyMemberEntity;
import com.cognifia.lms.accountfamily.dto.FamilyDTO;
import com.cognifia.lms.accountfamily.repository.FamilyEntityRepository;
import com.cognifia.lms.accountfamily.repository.FamilyMemberEntityRepository;
import com.cognifia.lms.common.annotation.log.LogMethodData;
import com.cognifia.lms.common.exception.AppMessageException;
import com.cognifia.lms.tokenprocess.TokenServicePort;

@Service
@AllArgsConstructor
public class FamilyService implements FamilyPort {

    private FamilyMemberEntityRepository memberRepository;
    private FamilyEntityRepository familyRepository;
    private AccountService accountService;
    private TokenServicePort tokenService;

    public List<FamilyDTO> getFamilyAccountList(int userId) {
        Optional<FamilyMemberEntity> option = memberRepository.findById(userId);
        if (!option.isPresent()) {
            throw new AppMessageException("family.userid.noexist");
        }

        FamilyMemberEntity member = option.get();
        List<FamilyDTO> results = new ArrayList<>();
        List<FamilyMemberEntity> members = memberRepository.findByFamilyId(member.getFamilyId());
        if ((members != null) && !members.isEmpty()) {
            members.forEach(x -> results.add(mapFamilyDTO(x)));
        }
        return results;
    }

    @Transactional
    @LogMethodData
    public String setFamilyMember(int askedForUserId, int updateUserId) {
        FamilyMemberEntity rmember = getByIdWithNull(updateUserId);
        FamilyMemberEntity amember = getByIdWithNull(askedForUserId);

        int familyId = getFamilyId(rmember, amember, updateUserId);
        if (rmember == null) {
            persistNewFamilyMember(familyId, updateUserId, updateUserId);
        }
        if (amember == null) {
            persistNewFamilyMember(familyId, askedForUserId, updateUserId);
        }
        if ((amember != null) && amember.isActive()) {
            throw new AppMessageException("family.userid.already-active");
        }
        return tokenService.getFamilyMemberToken(askedForUserId, updateUserId);
    }

    @Override
    public void activateFamilyMember(int askedForUserId, int requestedByUserId) {
        validateUserId(askedForUserId);
        FamilyMemberEntity rmember = getByIdWithNull(requestedByUserId);
        FamilyMemberEntity amember = getByIdWithNull(askedForUserId);
        if (rmember.getFamilyId() != amember.getFamilyId()) {
            throw new AppMessageException("family.userid.not-in-same-family");
        }
        if (!rmember.isActive()) {
            rmember.setActive(true);
            memberRepository.saveAndFlush(rmember);
        }

        if (!amember.isActive()) {
            amember.setActive(true);
            memberRepository.saveAndFlush(amember);
        }
    }

    private void validateUserId(int userId) {
        Account account = accountService.getAccountById(userId);
    }

    private FamilyMemberEntity getById(int userId) {
        Optional<FamilyMemberEntity> option = memberRepository.findById(userId);
        if (!option.isPresent()) {
            throw new AppMessageException("family.userid.noexist");
        }
        return option.get();
    }

    private FamilyMemberEntity getByIdWithNull(int userId) {
        return memberRepository.findById(userId).orElse(null);
    }

    private void persistNewFamilyMember(int familyId, int userId, int requestedByUserId) {
        FamilyMemberEntity entity = FamilyMemberEntity.builder().familyId(familyId).userId(userId).active(false)
                                                      .build();
        entity.setAudit(requestedByUserId);
        memberRepository.saveAndFlush(entity);
    }

    private int getFamilyId(FamilyMemberEntity rmember, FamilyMemberEntity amember, int requestedByUserId) {
        if ((rmember == null) && (amember == null)) {
            return getNewFamilyEntity(requestedByUserId).getFamilyId();
        }
        if ((rmember != null) && (amember != null) && rmember.isActive() && amember.isActive()) {
            throw new AppMessageException("family.userid.exist.family");
        }
        if (rmember != null) {
            return rmember.getFamilyId();
        }
        return amember.getFamilyId();
    }

    private FamilyEntity getNewFamilyEntity(int requestedByUserId) {
        FamilyEntity entity = FamilyEntity.builder().familyName(Integer.toString(requestedByUserId)).build();
        entity.setAudit(requestedByUserId);
        familyRepository.saveAndFlush(entity);
        return entity;
    }

    private FamilyDTO mapFamilyDTO(FamilyMemberEntity entity) {
        return FamilyDTO.builder().active(entity.isActive())
                        .userAccount(accountService.getAccountById(entity.getUserId())).build();
    }
}
