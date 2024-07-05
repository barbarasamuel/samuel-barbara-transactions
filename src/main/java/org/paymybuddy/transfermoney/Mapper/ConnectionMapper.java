package org.paymybuddy.transfermoney.Mapper;

import org.mapstruct.Mapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.model.ConnectionDTO;

@Mapper(componentModel="spring")
public interface ConnectionMapper extends AbstractMapper<Connection, ConnectionDTO>{

}
