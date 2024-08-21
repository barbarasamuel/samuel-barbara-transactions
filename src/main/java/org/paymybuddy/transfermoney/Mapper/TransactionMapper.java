package org.paymybuddy.transfermoney.Mapper;

import org.mapstruct.Mapper;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.TransactionDTO;

/**
 *
 * To map from Transactions object to TransactionsDTO object or inverse
 *
 */
@Mapper(componentModel="spring")
public interface TransactionMapper extends AbstractMapper<Transactions,TransactionDTO>{


}
