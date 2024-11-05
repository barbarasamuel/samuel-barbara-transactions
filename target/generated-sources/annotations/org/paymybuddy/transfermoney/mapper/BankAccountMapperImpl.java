package org.paymybuddy.transfermoney.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.model.BankAccountDTO;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-30T17:34:50+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class BankAccountMapperImpl implements BankAccountMapper {

    @Override
    public BankAccount convertToEntity(BankAccountDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BankAccount bankAccount = new BankAccount();

        bankAccount.setId( dto.getId() );
        bankAccount.setBalance( dto.getBalance() );
        bankAccount.setConnectionBankAccount( connectionDTOToConnection( dto.getConnectionBankAccount() ) );

        return bankAccount;
    }

    @Override
    public BankAccountDTO convertToDTO(BankAccount entity) {
        if ( entity == null ) {
            return null;
        }

        BankAccountDTO.BankAccountDTOBuilder bankAccountDTO = BankAccountDTO.builder();

        bankAccountDTO.id( entity.getId() );
        bankAccountDTO.connectionBankAccount( connectionToConnectionDTO( entity.getConnectionBankAccount() ) );
        bankAccountDTO.balance( entity.getBalance() );

        return bankAccountDTO.build();
    }

    @Override
    public List<BankAccountDTO> convertListToDTO(List<BankAccount> entity) {
        if ( entity == null ) {
            return null;
        }

        List<BankAccountDTO> list = new ArrayList<BankAccountDTO>( entity.size() );
        for ( BankAccount bankAccount : entity ) {
            list.add( convertToDTO( bankAccount ) );
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
