package com.cognifia.lms.transactionlog.service;

import com.cognifia.lms.transactionlog.domain.TransactionLog;
import com.cognifia.lms.transactionlog.dto.TransactionLogDTO;
import com.cognifia.lms.transactionlog.dto.TransactionLogDTOMapper;
import com.cognifia.lms.transactionlog.repository.TransactionLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionLogService {

    TransactionLogRepository repository ;

    public TransactionLogService(TransactionLogRepository repository) {
        this.repository = repository;
    }

    public List<TransactionLogDTO> getTransactionlogsByUuid(String uuid){
        List<TransactionLog> list = repository.findByUUID(uuid) ;
        if ((list == null)||(list.size() ==0)) return null ;
        List<TransactionLogDTO> retList = new ArrayList<>();
        list.forEach(x-> retList.add(getTransactionLogDTOFromEntity(x)));
        return retList ;
    }

    private TransactionLogDTO getTransactionLogDTOFromEntity(TransactionLog entity){
        return TransactionLogDTOMapper.INSTANCE.entityToDTO(entity) ;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistTransactionLog(String uuid, String typeCode, String message,
                                      String statusCode, int userId){
        TransactionLog entity = TransactionLog.builder()
                .userId(userId)
                .message(message)
                .statusCode(statusCode)
                .uuid(uuid)
                .transactionTypeCode(typeCode)
                .utimestamp(LocalDateTime.now())
                .build();

        repository.save(entity) ;
    }

}

