package org.paymybuddy.transfermoney.Mapper;

import org.mapstruct.Mapper;
import org.paymybuddy.transfermoney.entity.Contact;
import org.paymybuddy.transfermoney.model.ContactDTO;

@Mapper(componentModel="spring")
public interface ContactMapper  extends AbstractMapper<Contact, ContactDTO>{
}

