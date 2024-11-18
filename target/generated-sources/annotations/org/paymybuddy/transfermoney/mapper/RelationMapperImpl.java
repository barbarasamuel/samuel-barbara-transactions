package org.paymybuddy.transfermoney.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Relation;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.RelationDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-18T10:19:38+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class RelationMapperImpl implements RelationMapper {

    @Override
    public Relation convertToEntity(RelationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Relation relation = new Relation();

        relation.setId( dto.getId() );
        relation.setConnectionFriend( connectionDTOToConnection( dto.getConnectionFriend() ) );
        relation.setUser( connectionDTOToConnection( dto.getUser() ) );

        return relation;
    }

    @Override
    public RelationDTO convertToDTO(Relation entity) {
        if ( entity == null ) {
            return null;
        }

        RelationDTO.RelationDTOBuilder relationDTO = RelationDTO.builder();

        relationDTO.id( entity.getId() );
        relationDTO.connectionFriend( connectionToConnectionDTO( entity.getConnectionFriend() ) );
        relationDTO.user( connectionToConnectionDTO( entity.getUser() ) );

        return relationDTO.build();
    }

    @Override
    public List<RelationDTO> convertListToDTO(List<Relation> entity) {
        if ( entity == null ) {
            return null;
        }

        List<RelationDTO> list = new ArrayList<RelationDTO>( entity.size() );
        for ( Relation relation : entity ) {
            list.add( convertToDTO( relation ) );
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
