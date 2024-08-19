package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ContactMapper;
import org.paymybuddy.transfermoney.entity.Contact;
import org.paymybuddy.transfermoney.model.ContactDTO;
import org.paymybuddy.transfermoney.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    ContactMapper contactMapper;

    @Autowired
    ContactRepository contactRepository;

    public void addedMessage(ContactDTO newContactDTO){

        Contact contact = contactMapper.convertToEntity(newContactDTO);
        contactRepository.save(contact);

    }
}
