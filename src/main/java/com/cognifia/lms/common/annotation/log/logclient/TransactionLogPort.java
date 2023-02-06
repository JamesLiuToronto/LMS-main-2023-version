package com.cognifia.lms.common.annotation.log.logclient;

public interface TransactionLogPort {

    String persistTransactionLog(String uuid, String typeCode, String message, String statusCode, int userId);
}
