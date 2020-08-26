package com.enigma.sepotipi.specification;

import com.enigma.sepotipi.entity.WalletHistory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

public class HistorySpecification {

    public static Specification<WalletHistory> groupByType(){
        return new Specification<WalletHistory>() {
            @Override
            public Predicate toPredicate(Root<WalletHistory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.groupBy(root.get("type"));
                return null;
            }
        };
    }
}
