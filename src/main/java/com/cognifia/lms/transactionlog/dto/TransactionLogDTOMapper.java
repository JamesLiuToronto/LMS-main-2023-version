package com.cognifia.lms.transactionlog.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionLogDTOMapper {
    TransactionLogDTOMapper INSTANCE = Mappers.getMapper(TransactionLogDTOMapper.class);

    TransactionLogDTO entityToDTO(final com.cognifia.lms.transactionlog.domain.TransactionLog entity);
    com.cognifia.lms.transactionlog.domain.TransactionLog dtoToEntity(TransactionLogDTO dto);

}
