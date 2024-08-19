package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository  extends CrudRepository<Contact,Long> {
}
