package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Wallet;
import com.enigma.sepotipi.entity.WalletHistory;
import com.enigma.sepotipi.exception.BalanceNotEnoughToTakeThisActionException;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.WalletHistoryRepository;
import com.enigma.sepotipi.services.WalletHistoryServices;
import com.enigma.sepotipi.services.WalletServices;
import com.enigma.sepotipi.specification.HistorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class WalletHistoryServicesDBImpl implements WalletHistoryServices {

    @Autowired
    WalletHistoryRepository walletHistoryRepository;

    @Autowired
    WalletServices walletServices;

    @Override
    public WalletHistory saveWalletHistory(WalletHistory walletHistory) {
        Wallet wallet = walletServices.getWallet(walletHistory.getWalletId());
        walletHistory.setTrxDate(new Timestamp(new Date().getTime()));
        switch (walletHistory.getType()){
            case TOPUP:
                wallet.setBalance(wallet.getBalance()+walletHistory.getAmount());
                break;
            case WITHDRAWAL:
                if(wallet.getBalance() >= walletHistory.getAmount()){
                    wallet.setBalance(wallet.getBalance() - walletHistory.getAmount());
                } else throw new BalanceNotEnoughToTakeThisActionException();
        }
        walletHistory.setWallet(wallet);
        walletServices.saveWallet(wallet);
        return walletHistoryRepository.save(walletHistory);
    }

    @Override
    public WalletHistory getWalletHistory(String id) {
        WalletHistory history = null;
        if(walletHistoryRepository.findById(id).isPresent()){
            history = walletHistoryRepository.findById(id).get();
        } else throw new ResourceNotFoundException();
        return history;
    }

    @Override
    public Page<WalletHistory> getAllWalletHistories(Pageable pageable) {
        Page<WalletHistory> histories = walletHistoryRepository
                .findAll(HistorySpecification.groupByType(), pageable);
        return histories;
    }

    @Override
    public void deleteWalletHistory(String id) {

    }

    @Override
    public void payment(WalletHistory walletHistory) {
        walletHistoryRepository.save(walletHistory);
    }
}
