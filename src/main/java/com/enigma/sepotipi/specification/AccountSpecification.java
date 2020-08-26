package com.enigma.sepotipi.specification;

import com.enigma.sepotipi.entity.Account;
import com.enigma.sepotipi.entity.Profile;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;

public class AccountSpecification {

    public static Specification<Account> findByCriteria(Account account){
        return new Specification<Account>() {
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                final Collection<Predicate> predicates = new ArrayList<>();
                final Join<Account, Profile> profileJoin = root.join("profile", JoinType.INNER);
                if(account!=null){
                    if(account.getActive()!=null){
                        final Predicate accountActivatePredicate = criteriaBuilder
                                .isTrue(root.get("isActive").as(Boolean.class));
                        predicates.add(accountActivatePredicate);
                    }
                }
                criteriaQuery.distinct(true);
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
