package org.paymybuddy.transfermoney.mapper;

import org.mapstruct.Mapper;
import org.paymybuddy.transfermoney.entity.Contact;
import org.paymybuddy.transfermoney.model.ContactDTO;

/**
 *
 * To map from Contact object to ContactDTO object or inverse
 *
 */
@Mapper(componentModel="spring")
public interface ContactMapper  extends AbstractMapper<Contact, ContactDTO>{
}

