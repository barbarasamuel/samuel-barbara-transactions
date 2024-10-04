package org.paymybuddy.transfermoney.service;


import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.Mapper.TransactionMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionsService {
    @Autowired
    TransactionsRepository transactionsRepository;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    ConnectionMapper connectionMapper;
    //@Autowired
    //ConnectionsRepository connectionsRepository;
    @Autowired
    ConnectionsService connectionsService;
    @Autowired
    RelationService relationService;
    @Autowired
    BankAccountService bankAccountService;



    /**
     *
     * To save a new transaction
     *
     */
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        Transactions transactions = transactionMapper.convertToEntity(transactionDTO);


        transactions = transactionsRepository.save(transactions);

        return transactionMapper.convertToDTO(transactions);
    }

    public TransferPageDTO accessTransferPage(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ConnectionDTO connectionDTO = connectionsService.getIdentifiant(userDetails.getUsername());
        List<ConnectionDTO> allConnectionsList = connectionsService.getAllConnections();
        List<RelationsConnection> connectionsList = relationService.getRelations(connectionDTO);
        List<TransactionsConnection> transactionsList = getTransactionsFromUser(connectionDTO);
        List<BankAccountDTO> bankAccountDTOList = bankAccountService.getUserAccountsList(connectionDTO);

        return TransferPageDTO.builder()
                .connectionDTO(connectionDTO)
                .allConnectionsList(allConnectionsList)
                .connectionsList(connectionsList)
                .transactionsList(transactionsList)
                .bankAccountDTOList(bankAccountDTOList)
                .build();
    }
    /**
     *
     * To get the list of transactions from the user
     *
     */
    public List<TransactionDTO> getTransactions(Long idDebtorAccount){
        List<TransactionsConnection> transactionsConnectionList = new ArrayList<>();
        List<Transactions> transactionsEntityList = transactionsRepository.findByDebtorId(idDebtorAccount);
        List<TransactionDTO> transactionsDTOList= transactionMapper.convertListToDTO(transactionsEntityList);
        for(TransactionDTO transactionDTO:transactionsDTOList){
            TransactionsConnection transactionsConnection = new TransactionsConnection(
                    transactionDTO.getTransactionDate(),
                    transactionDTO.getCreditor().getName(),
                    transactionDTO.getDescription(),
                    transactionDTO.getAmount()
            );
            transactionsConnectionList.add(transactionsConnection);
        }
        return transactionMapper.convertListToDTO(transactionsEntityList);
    }

    /**
     *
     * To get the user transactions
     *
     */
    public List<TransactionsConnection> getTransactionsFromUser(ConnectionDTO user){

        List<TransactionsConnection> transactionsConnectionList = new ArrayList<>();
        List<Transactions> transactionsEntityList = transactionsRepository.findByDebtorId(user.getId());
        List<TransactionDTO> transactionsDTOList= transactionMapper.convertListToDTO(transactionsEntityList);
        for(TransactionDTO transactionDTO:transactionsDTOList){
            TransactionsConnection transactionsConnection = new TransactionsConnection(
                    transactionDTO.getTransactionDate(),
                    transactionDTO.getCreditor().getName(),
                    transactionDTO.getDescription(),
                    transactionDTO.getAmount()
            );
            transactionsConnectionList.add(transactionsConnection);
        }
        return transactionsConnectionList;

    }

    /**
     *
     * To manage the pagination by page and sorted by date by default
     *
     */
    public Page<Transactions> findPaginated(ConnectionDTO debtorDTO, int pageNo, int pageSize, String sortField, String sortDirection){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).descending() :
                Sort.by(sortField).ascending();

        Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Connection debtor = connectionMapper.convertToEntity(debtorDTO);
        Page<Transactions> transactions = transactionsRepository.findAllByDebtor(debtor,pageable);
        return transactions;
    }

}
