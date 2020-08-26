package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Account;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.AccountRepository;
import com.enigma.sepotipi.services.AccountServices;
import com.enigma.sepotipi.services.ProfileServices;
import com.enigma.sepotipi.services.WalletServices;
import com.enigma.sepotipi.specification.AccountSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountServicesDBImpl implements AccountServices {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProfileServices profileServices;

    @Autowired
    WalletServices walletServices;

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(String id) {
        Account account = null;
        if(accountRepository.findById(id).isPresent()) {
            account = accountRepository.findById(id).get();
        } else throw new ResourceNotFoundException();
        return  account;
    }

    @Override
    public Page<Account> getAllAccount(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Page<Account> getAllActiveAccounts(Account account, Pageable pageable) {
        return  accountRepository.findAll(AccountSpecification.findByCriteria(account), pageable);
    }

    @Override
    public void deleteAccount(String id) {
        Account account = accountRepository.findById(id).get();
        account.setActive(false);
        accountRepository.save(account);
    }
}
