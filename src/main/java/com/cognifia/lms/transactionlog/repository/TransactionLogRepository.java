package com.cognifia.lms.transactionlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.cognifia.lms.transactionlog.domain.TransactionLog;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog,Integer> {

    List<TransactionLog> findByUUID(String uuid) ;
}
