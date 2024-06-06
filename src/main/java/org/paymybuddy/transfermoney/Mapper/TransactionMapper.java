package org.paymybuddy.transfermoney.Mapper;

import org.mapstruct.Mapper;
import org.paymybuddy.transfermoney.entity.TransactionEntity;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.springframework.core.annotation.AnnotationUtils.getValue;

@Mapper(componentModel="spring")
public interface TransactionMapper extends AbstractMapper<TransactionEntity,TransactionDTO>{


}
