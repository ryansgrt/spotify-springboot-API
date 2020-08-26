package com.enigma.sepotipi.controller;

import com.enigma.sepotipi.entity.Account;
import com.enigma.sepotipi.services.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountServices accountServices;

    @PostMapping
    public Account saveAccount(@RequestBody Account account){
        return accountServices.saveAccount(account);
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable String id){
        return accountServices.getAccount(id);
    }

    @GetMapping
    public Page<Account> getAllAccount(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return accountServices.getAllAccount(pageable);
    }

    @GetMapping("/status/{status}")
    public Page<Account> getAllActiveAccounts(@PathVariable Account status,@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return accountServices.getAllActiveAccounts(status, pageable);
    }

    @PutMapping("/{id}")
    public void deactivateAccount(@PathVariable String id){
        accountServices.deleteAccount(id);
    }
}
