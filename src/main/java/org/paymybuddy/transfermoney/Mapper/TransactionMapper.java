package org.paymybuddy.transfermoney.Mapper;

import org.mapstruct.Mapper;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.TransactionDTO;


@Mapper(componentModel="spring")
public interface TransactionMapper extends AbstractMapper<Transactions,TransactionDTO>{


}
