package com.cognifia.lms.common.annotation.log.logclient;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import com.cognifia.lms.transactionlog.service.TransactionLogService;

@Component
@AllArgsConstructor
public class TransactionLogAdapter implements TransactionLogPort {

    private TransactionLogService logService;

    @Override
    public String persistTransactionLog(String uuid, String typeCode, String message, String statusCode, int userId) {
        logService.persistTransactionLog(uuid, typeCode, message, statusCode, userId);
        return "1";
    }
}
