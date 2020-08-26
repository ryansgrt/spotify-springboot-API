package com.enigma.sepotipi.services;

import com.enigma.sepotipi.entity.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletServices {

    public Wallet saveWallet(Wallet wallet);
    public Wallet getWallet(String id);
    public Page<Wallet> getAllWallets(Pageable pageable);
    public void deleteWallet(String id);
}
