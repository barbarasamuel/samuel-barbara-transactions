package org.paymybuddy.transfermoney.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-06T15:12:11+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transactions convertToEntity(TransactionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Transactions.TransactionsBuilder transactions = Transactions.builder();

        transactions.description( dto.getDescription() );
        transactions.amount( dto.getAmount() );
        transactions.transactionDate( dto.getTransactionDate() );
        transactions.creditor( connectionDTOToConnection( dto.getCreditor() ) );
        transactions.debtor( connectionDTOToConnection( dto.getDebtor() ) );

        return transactions.build();
    }

    @Override
    public TransactionDTO convertToDTO(Transactions entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionDTO.TransactionDTOBuilder transactionDTO = TransactionDTO.builder();

        transactionDTO.id( entity.getId() );
        transactionDTO.description( entity.getDescription() );
        transactionDTO.amount( entity.getAmount() );
        transactionDTO.transactionDate( entity.getTransactionDate() );
        transactionDTO.creditor( connectionToConnectionDTO( entity.getCreditor() ) );
        transactionDTO.debtor( connectionToConnectionDTO( entity.getDebtor() ) );

        return transactionDTO.build();
    }

    @Override
    public List<TransactionDTO> convertListToDTO(List<Transactions> entity) {
        if ( entity == null ) {
            return null;
        }

        List<TransactionDTO> list = new ArrayList<TransactionDTO>( entity.size() );
        for ( Transactions transactions : entity ) {
            list.add( convertToDTO( transactions ) );
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
