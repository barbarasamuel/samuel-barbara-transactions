package org.paymybuddy.transfermoney.service;


import org.paymybuddy.transfermoney.mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.mapper.TransactionMapper;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionsService {
    @Autowired
    TransactionsRepository transactionsRepository;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    ConnectionMapper connectionMapper;


    /**
     *
     * To save a new transaction
     *
     */
    public Transactions saveTransaction(Transactions transaction) {

        return transactionsRepository.save(transaction);

    }

    /**
     *
     * To get the list of transactions from the user
     *
     */
    public List<Transactions> getTransactions(Long idDebtorAccount){

        return transactionsRepository.findByDebtorId(idDebtorAccount);
    }

    /**
     *
     * To get the user transactions
     *
     */
    public List<TransactionsConnection> getTransactionsFromUser(Connection user){

        List<TransactionsConnection> transactionsConnectionList = new ArrayList<>();
        List<Transactions> transactionsEntityList = transactionsRepository.findByDebtorId(user.getId());

        for(Transactions transaction:transactionsEntityList){
            TransactionsConnection transactionsConnection = new TransactionsConnection(
                    transaction.getTransactionDate(),
                    transaction.getCreditor().getName(),
                    transaction.getDescription(),
                    transaction.getAmount()
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
    public Page<Transactions> findPaginated(Connection debtor, int pageNo, int pageSize, String sortField, String sortDirection){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).descending() :
                Sort.by(sortField).ascending();

        Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        return transactionsRepository.findAllByDebtor(debtor,pageable);
    }

}
