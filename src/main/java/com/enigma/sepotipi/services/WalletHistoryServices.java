package com.enigma.sepotipi.services;

import com.enigma.sepotipi.entity.WalletHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletHistoryServices {

    public WalletHistory saveWalletHistory(WalletHistory walletHistory);
    public WalletHistory getWalletHistory(String id);
    public Page<WalletHistory> getAllWalletHistories(Pageable pageable);
    public void deleteWalletHistory(String id);
    public void payment(WalletHistory walletHistory);
}
