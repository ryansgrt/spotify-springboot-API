package com.enigma.sepotipi.services.impl;
import com.enigma.sepotipi.entity.*;
import com.enigma.sepotipi.enums.HistoryTypeEnum;
import com.enigma.sepotipi.exception.TransactionCannotBeDoneException;
import com.enigma.sepotipi.repository.TransactionRepository;
import com.enigma.sepotipi.services.*;
import com.enigma.sepotipi.specification.TransactionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class TransactionServicesDBImpl implements TransactionServices {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    WalletServices walletServices;
    @Autowired
    SongServices songServices;
    @Autowired
    AlbumServices albumServices;
    @Autowired
    WalletHistoryServices walletHistoryServices;
    @Override
    public List<Transaction> saveTransaction(Transaction transaction) {
        List<Transaction> transactions = transactionRepository.findAll();
        List<String> itemsId = transaction.getItemId();
        List<Transaction> saveTrans = new ArrayList<Transaction>();
        for(String itemId : itemsId){
            for(Transaction transaction1 : transactions){
                if(!transaction1.getItem().getId().equals(itemId)
                        || !transaction1.getWallet().getId().equals(transaction.getWalletId())){
                    Song song = transaction.getItem();
                    if(song == null) {
                        song = songServices.getSong(itemId);
                        transaction.setItem(song);
                    }
                    Wallet wallet = walletServices.getWallet(transaction.getWalletId());
                    transaction.setWallet(wallet);
                    if(wallet.getBalance() < song.getPrice()){
                        throw new TransactionCannotBeDoneException();
                    }
                    saveTrans.add(save(transaction, song, wallet));
                }
            }
        }
        return transactionRepository.saveAll(saveTrans);

    }
    @Override
    public Transaction getTransaction(String id) {
        Transaction transaction = null;
        if(transactionRepository.findById(id).isPresent()){
            transaction = transactionRepository.findById(id).get();
        }
        return transaction;
    }
    @Override
    public Page<Transaction> searchTransactions(Pageable pageable, Transaction searchForm) {
        Page<Transaction> transactions = transactionRepository
                .findAll(TransactionSpecification
                        .findByCriteria(searchForm), pageable);
        return transactions;
    }
    @Override
    public void deleteTransaction(String id) {
        transactionRepository.deleteById(id);
    }
    @Override
    public List<Transaction> buyByAlbum(String albumId, Transaction transaction) {
        List<Transaction> saveTrans = new ArrayList<Transaction>();
        List<Transaction> transactions = transactionRepository.findAll();
        Album album = albumServices.getAlbum(albumId);
        Wallet wallet = walletServices.getWallet(transaction.getWalletId());
        Double total = 0.0;
        List<Song> songs = album.getSongs();
        for(Song song: songs){
            for(Transaction trans: transactions){
                if(song.getId().equals(trans.getItem().getId())
                        && wallet.getId().equals(trans.getWallet().getId())){
                    throw new TransactionCannotBeDoneException();
                }
            }
            total += song.getPrice();
        }
        if(wallet.getBalance() < total ){
            throw new TransactionCannotBeDoneException();
        }
        transaction.setAlbumDiscount(album.getDiscount());
        for(Song song: songs){
            Transaction songTrans = new Transaction();
            songTrans.setItem(song);
            songTrans.setWalletId(transaction.getWalletId());
            songTrans.setAlbumDiscount(album.getDiscount());
            songTrans = save(transaction, song, wallet);
            saveTrans.add(songTrans);
        }
        return transactionRepository.saveAll(saveTrans);
    }

    public Transaction save(Transaction transaction, Song song, Wallet wallet){
        transaction.setAmount(song.getPrice()*(1-transaction.getAlbumDiscount()));
        transaction.setTrxDate(new Timestamp(new Date().getTime()));
        wallet.setBalance(wallet.getBalance()-transaction.getAmount());
        walletServices.saveWallet(wallet);
        WalletHistory walletHistory = new WalletHistory();
        walletHistory.setAmount(transaction.getAmount());
        walletHistory.setTrxDate(transaction.getTrxDate());
        walletHistory.setType(HistoryTypeEnum.PAYMENT);
        walletHistory.setWallet(wallet);
        walletHistoryServices.payment(walletHistory);
        return transaction;
    }
}