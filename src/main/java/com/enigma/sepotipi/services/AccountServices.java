package com.enigma.sepotipi.services;

import com.enigma.sepotipi.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AccountServices {

    public Account saveAccount(Account account);
    public Account getAccount(String id);
    public Page<Account> getAllAccount(Pageable pageable);
    public Page<Account> getAllActiveAccounts(Account account, Pageable pageable);
    public void deleteAccount(String id);
}
