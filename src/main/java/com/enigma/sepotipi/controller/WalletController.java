package com.enigma.sepotipi.controller;

import com.enigma.sepotipi.entity.Wallet;
import com.enigma.sepotipi.services.WalletServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/wallet")
public class WalletController {

    @Autowired
    WalletServices walletServices;

    @PostMapping
    public Wallet saveWallet(@RequestBody Wallet wallet){
        return walletServices.saveWallet(wallet);
    }

    @GetMapping("/{id}")
    public Wallet getWallet(@PathVariable String id){
        return walletServices.getWallet(id);
    }

    @GetMapping
    public Page<Wallet> getAllWallets(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return walletServices.getAllWallets(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteWallet(@PathVariable String id){
        walletServices.deleteWallet(id);
    }
}
