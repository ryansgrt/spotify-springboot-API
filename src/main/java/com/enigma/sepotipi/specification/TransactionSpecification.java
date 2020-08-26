package com.enigma.sepotipi.specification;

import com.enigma.sepotipi.entity.Song;
import com.enigma.sepotipi.entity.Transaction;
import com.enigma.sepotipi.entity.Wallet;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;

public class TransactionSpecification {

    public static Specification<Transaction> findByCriteria(Transaction transaction){
        return new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                final Collection<Predicate> predicates = new ArrayList<>();
                final Join<Transaction, Song> songJoin = root.join("item", JoinType.INNER);
                final Join<Transaction, Wallet> walletJoin = root.join("wallet", JoinType.INNER);
                if(transaction!=null){
                    if(transaction.getItem()!=null){
                        final Predicate itemPredicate = criteriaBuilder
                                .like(criteriaBuilder
                                        .lower(songJoin.get("title")),
                                        "%" + transaction.getItem().getTitle() + "%");
                        predicates.add(itemPredicate);
                    }
                    if(transaction.getWallet()!=null){
                        final Predicate walletPredicate = criteriaBuilder
                                .equal(walletJoin.get("id"), transaction.getWalletId());
                        predicates.add(walletPredicate);
                    }

                }
                criteriaQuery.distinct(true);
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
