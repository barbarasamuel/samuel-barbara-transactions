package org.paymybuddy.transfermoney.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Contact;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.ContactDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-06T15:12:11+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public Contact convertToEntity(ContactDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setId( dto.getId() );
        contact.setMessage( dto.getMessage() );
        contact.setSender( connectionDTOToConnection( dto.getSender() ) );

        return contact;
    }

    @Override
    public ContactDTO convertToDTO(Contact entity) {
        if ( entity == null ) {
            return null;
        }

        ContactDTO.ContactDTOBuilder contactDTO = ContactDTO.builder();

        contactDTO.id( entity.getId() );
        contactDTO.message( entity.getMessage() );
        contactDTO.sender( connectionToConnectionDTO( entity.getSender() ) );

        return contactDTO.build();
    }

    @Override
    public List<ContactDTO> convertListToDTO(List<Contact> entity) {
        if ( entity == null ) {
            return null;
        }

        List<ContactDTO> list = new ArrayList<ContactDTO>( entity.size() );
        for ( Contact contact : entity ) {
            list.add( convertToDTO( contact ) );
        }

        return list;
    }

    protected Connection connectionDTOToConnection(ConnectionDTO connectionDTO) {
        if ( connectionDTO == null ) {
            return null;
        }

        Connection connection = new Connection();

        connection.setId( connectionDTO.getId() );
        connection.setName( connectionDTO.getName() );
        connection.setEmail( connectionDTO.getEmail() );
        connection.setPassword( connectionDTO.getPassword() );

        return connection;
    }

    protected ConnectionDTO connectionToConnectionDTO(Connection connection) {
        if ( connection == null ) {
            return null;
        }

        ConnectionDTO.ConnectionDTOBuilder connectionDTO = ConnectionDTO.builder();

        connectionDTO.id( connection.getId() );
        connectionDTO.name( connection.getName() );
        connectionDTO.email( connection.getEmail() );
        connectionDTO.password( connection.getPassword() );

        return connectionDTO.build();
    }
}
