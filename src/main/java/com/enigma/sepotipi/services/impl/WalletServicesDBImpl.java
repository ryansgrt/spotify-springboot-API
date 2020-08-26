package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Account;
import com.enigma.sepotipi.entity.Wallet;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.WalletRepository;
import com.enigma.sepotipi.services.AccountServices;
import com.enigma.sepotipi.services.WalletServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WalletServicesDBImpl implements WalletServices {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    AccountServices accountServices;

    @Override
    public Wallet saveWallet(Wallet wallet) {
        Account account = wallet.getOwner();
        if(account == null) {
            wallet.setOwner(accountServices.getAccount(wallet.getOwnerId()));
        }
        return walletRepository.save(wallet);    }

    @Override
    public Wallet getWallet(String id) {
        Wallet wallet = null;
        if(walletRepository.findById(id).isPresent()){
            wallet = walletRepository.findById(id).get();
        } else throw  new ResourceNotFoundException();
        return wallet;
    }

    @Override
    public Page<Wallet> getAllWallets(Pageable pageable) {
        return walletRepository.findAll(pageable);
    }

    @Override
    public void deleteWallet(String id) {
        walletRepository.deleteById(id);
    }
}
