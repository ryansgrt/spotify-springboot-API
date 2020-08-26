package com.enigma.sepotipi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mst_wallet")
public class Wallet {

    @Id
    @GeneratedValue(generator = "artist_uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "artist_uuid", strategy = "uuid")
    private String id;
    private Double balance;

    @OneToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties(value = "wallet")
    private Account owner;

    @OneToMany(mappedBy = "wallet")
    @JsonIgnoreProperties(value = "wallet")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "wallet")
    @JsonIgnoreProperties(value = "wallet")
    private List<WalletHistory> histories;

    @Transient
    private String ownerId;

    public Wallet() {
    }

    public Wallet(String id, Double balance, String ownerId) {
        this.id = id;
        this.balance = balance;
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<WalletHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<WalletHistory> histories) {
        this.histories = histories;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(id, wallet.id) &&
                Objects.equals(balance, wallet.balance) &&
                Objects.equals(owner, wallet.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, owner);
    }
}
