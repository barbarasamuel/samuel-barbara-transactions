package org.paymybuddy.transfermoney.mapper;

import org.mapstruct.Mapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.model.ConnectionDTO;

/**
 *
 * To map from Connection object to ConnectionDTO object or inverse
 *
 */
@Mapper(componentModel="spring")
public interface ConnectionMapper extends AbstractMapper<Connection, ConnectionDTO>{

}
