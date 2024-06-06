package org.paymybuddy.transfermoney.Mapper;

import org.mapstruct.Mapper;
import org.paymybuddy.transfermoney.entity.ConnectionsEntity;
import org.paymybuddy.transfermoney.entity.TransactionEntity;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.TransactionDTO;

@Mapper(componentModel="spring")
public interface ConnectionMapper extends AbstractMapper<ConnectionsEntity, ConnectionDTO>{

}
