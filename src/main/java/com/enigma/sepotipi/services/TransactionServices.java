package com.enigma.sepotipi.services;

import com.enigma.sepotipi.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionServices {

    public List<Transaction> saveTransaction(Transaction transaction);
    public Transaction getTransaction(String id);
    public Page<Transaction> searchTransactions(Pageable pageable, Transaction searchForm);
    public void deleteTransaction(String id);
    public List<Transaction> buyByAlbum(String albumId, Transaction transaction);
}
