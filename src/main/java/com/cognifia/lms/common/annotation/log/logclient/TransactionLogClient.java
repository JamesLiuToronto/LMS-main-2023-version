package com.cognifia.lms.common.annotation.log.logclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "transactionlogclient", url = "${transactionlog.url}")
public interface TransactionLogClient {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String persistTransactionLog(@RequestParam(name = "uuid") String uuid,
                                 @RequestParam(name = "typeCode") String typeCode,
                                 @RequestParam(name = "message") String message,
                                 @RequestParam(name = "statusCode") String statusCode,
                                 @RequestHeader(name = "updateUserId") int updateUserId);
}

