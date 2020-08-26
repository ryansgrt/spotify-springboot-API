package com.enigma.sepotipi.controller;
import com.enigma.sepotipi.entity.Transaction;
import com.enigma.sepotipi.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/account/transaction")
public class TransactionController {

    @Autowired
    TransactionServices transactionServices;
    @PostMapping
    public List<Transaction> saveTransaction(@RequestBody Transaction transaction){
        return transactionServices.saveTransaction(transaction);
    }
    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable String id){
        return transactionServices.getTransaction(id);
    }

    @GetMapping("/search")
    public Page<Transaction> getAllTransaction(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size, @RequestBody Transaction transaction){
        Pageable pageable = PageRequest.of(page-1, size);
        return transactionServices.searchTransactions(pageable, transaction);
    }
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable String id){
        transactionServices.deleteTransaction(id);
    }

    @PostMapping("/album/{id}")
    public List<Transaction> buyByAlbum(@PathVariable String id, @RequestBody Transaction transaction){
        return transactionServices.buyByAlbum(id, transaction);
    }
}