package org.paymybuddy.transfermoney.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-18T14:39:20+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class ConnectionMapperImpl implements ConnectionMapper {

    @Override
    public Connection convertToEntity(ConnectionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Connection connection = new Connection();

        connection.setId( dto.getId() );
        connection.setName( dto.getName() );
        connection.setEmail( dto.getEmail() );
        connection.setPassword( dto.getPassword() );

        return connection;
    }

    @Override
    public ConnectionDTO convertToDTO(Connection entity) {
        if ( entity == null ) {
            return null;
        }

        ConnectionDTO.ConnectionDTOBuilder connectionDTO = ConnectionDTO.builder();

        connectionDTO.id( entity.getId() );
        connectionDTO.name( entity.getName() );
        connectionDTO.email( entity.getEmail() );
        connectionDTO.password( entity.getPassword() );

        return connectionDTO.build();
    }

    @Override
    public List<ConnectionDTO> convertListToDTO(List<Connection> entity) {
        if ( entity == null ) {
            return null;
        }

        List<ConnectionDTO> list = new ArrayList<ConnectionDTO>( entity.size() );
        for ( Connection connection : entity ) {
            list.add( convertToDTO( connection ) );
        }

        return list;
    }
}
