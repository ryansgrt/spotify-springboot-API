package com.enigma.sepotipi.controller;

import com.enigma.sepotipi.entity.WalletHistory;
import com.enigma.sepotipi.services.WalletHistoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/wallet/history")
public class WalletHistoryController {

    @Autowired
    WalletHistoryServices walletHistoryServices;

    @PostMapping
    public WalletHistory saveWalletHistory(@RequestBody WalletHistory history){
        return walletHistoryServices.saveWalletHistory(history);
    }

    @GetMapping("/{id}")
    public WalletHistory getWalletHistory(@PathVariable String id){
        return walletHistoryServices.getWalletHistory(id);
    }

    @GetMapping
    public Page<WalletHistory> getAllWalletHistory(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return walletHistoryServices.getAllWalletHistories(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteWalletHistory(@PathVariable String id){
        walletHistoryServices.deleteWalletHistory(id);
    }
}
