package org.paymybuddy.transfermoney.service;

import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.Mapper.TransactionMapper;
//import org.hibernate.query.Page;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.TransactionDTO;
import org.paymybuddy.transfermoney.model.TransactionsConnection;
import org.paymybuddy.transfermoney.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    TransactionUtils transactionUtils;

    //private final List<Book> transactions = TransactionUtils.buildTransactions();

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
    //@Override
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

    //public Page<TransactionDTO> getPage(SpringDataWebProperties.Pageable pageable){
    /*public PageImpl<Book> findPaginated(Pageable pageable){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Book> list;

        if (transactions.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem + pageSize, transactions.size());
            list = transactions.subList(startItem,toIndex);
        }

        return new PageImpl<Book>(list, PageRequest.of(currentPage, pageSize),transactions.size());
    }*/
    public Page<Transactions> findPaginated(int pageNo, int pageSize){
        /*public Page<TransactionsConnection> findPaginated(int pageNo, int pageSize, Date transactionDate){
        Sort sort = sortTransactions.equalsIgnoreCase(Sort.Transactions.ASC.name()) ? Sort.by(transactionDate).ascending() :
                Sort.by(transactionDate).descending();*/


        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Transactions> transactions = transactionsRepository.findAll(pageable);
        return transactions;
    }

}
