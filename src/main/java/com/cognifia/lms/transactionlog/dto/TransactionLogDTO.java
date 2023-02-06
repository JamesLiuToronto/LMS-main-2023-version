package com.cognifia.lms.transactionlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TransactionLogDTO {

    //int transactionLogID ;
    @NonNull
    String uuid ;

    String transactionTypeCode ;

    String message ;
    String statusCode ;

    int userId;
    LocalDateTime utimestamp ;
}
