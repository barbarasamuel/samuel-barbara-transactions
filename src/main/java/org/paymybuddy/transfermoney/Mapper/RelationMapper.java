package org.paymybuddy.transfermoney.Mapper;


import org.mapstruct.Mapper;
import org.paymybuddy.transfermoney.entity.Relation;
import org.paymybuddy.transfermoney.model.RelationDTO;

/**
 *
 * To map from Relation object to RelationDTO object or inverse
 *
 */
@Mapper(componentModel="spring")
public interface RelationMapper  extends AbstractMapper<Relation, RelationDTO>{
}
