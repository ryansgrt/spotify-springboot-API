package com.enigma.sepotipi.specification;

import com.enigma.sepotipi.entity.Artist;
import com.enigma.sepotipi.entity.Song;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;

public class ArtistSpecification {

    public static Specification<Artist> findByCriteria(Artist artist){
        return new Specification<Artist>() {
            @Override
            public Predicate toPredicate(Root<Artist> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                final Collection<Predicate> predicates = new ArrayList<>();
                Join<Artist, Song> songJoin = root.join("songs", JoinType.INNER);
                if(artist!=null){
                    if(artist.getName()!=null){
                        final Predicate artistNamePredicate = criteriaBuilder
                                .like(criteriaBuilder
                                        .lower(root.get("name")),"%" + artist.getName().toLowerCase() + "%");
                        predicates.add(artistNamePredicate);
                    }
                    if(artist.getSongs()!=null){
                        for (Song song:artist.getSongs()) {
                            final Predicate songTitlePredicate = criteriaBuilder
                                    .like(criteriaBuilder
                                            .lower(songJoin.get("title")), "%" + song.getTitle().toLowerCase() + "%");
                            predicates.add(songTitlePredicate);
                        }
                    }
                }
                criteriaQuery.distinct(true);
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
