package org.paymybuddy.transfermoney.Mapper;

import org.mapstruct.Mapper;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.model.BankAccountDTO;

/**
 *
 * To map from BankAccount object to BankAccountDTO object or inverse
 *
 */
@Mapper(componentModel="spring")
public interface BankAccountMapper extends AbstractMapper<BankAccount, BankAccountDTO>{
}

