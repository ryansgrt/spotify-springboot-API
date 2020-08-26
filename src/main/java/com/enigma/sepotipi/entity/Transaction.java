package com.enigma.sepotipi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mst_transaction")
public class Transaction {

    @Id
    @GeneratedValue(generator = "artist_uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "artist_uuid", strategy = "uuid")
    private String id;
    private Timestamp trxDate;
    private Double amount;
    private Double albumDiscount;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties(value = {"transactions","genre","album","artist"})
    private Song item;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @JsonIgnoreProperties(value = {"transactions", "histories"})
    private Wallet wallet;

    @Transient
    private List<String> itemId;

    @Transient
    private String walletId;

    public Transaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Timestamp trxDate) {
        this.trxDate = trxDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAlbumDiscount() {
        return albumDiscount;
    }

    public void setAlbumDiscount(Double albumDiscount) {
        this.albumDiscount = albumDiscount;
    }

    public Song getItem() {
        return item;
    }

    public void setItem(Song item) {
        this.item = item;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public List<String> getItemId() {
        return itemId;
    }

    public void setItemId(List<String> itemId) {
        this.itemId = itemId;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(trxDate, that.trxDate) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(albumDiscount, that.albumDiscount) &&
                Objects.equals(item, that.item) &&
                Objects.equals(wallet, that.wallet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trxDate, amount, albumDiscount, item, wallet);
    }
}
