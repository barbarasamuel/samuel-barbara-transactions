package org.paymybuddy.transfermoney.repository;

import org.paymybuddy.transfermoney.entity.Contact;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * To get the information from the database linked to the contact table
 *
 */
public interface ContactRepository  extends CrudRepository<Contact,Long> {
}
