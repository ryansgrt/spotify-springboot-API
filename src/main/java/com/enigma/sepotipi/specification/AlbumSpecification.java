package com.enigma.sepotipi.specification;

import com.enigma.sepotipi.entity.Album;
import com.enigma.sepotipi.entity.Artist;
import com.enigma.sepotipi.entity.Song;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;

public class AlbumSpecification {

    public static Specification<Album> findByCriteria(Album album){
        return new Specification<Album>() {
            @Override
            public Predicate toPredicate(Root<Album> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                final Collection<Predicate> predicates = new ArrayList<>();
                Join<Album, Song> songJoin = root.join("songs", JoinType.INNER);
                if(album!=null){
                    if(!StringUtils.isEmpty(album.getTitle())){
                        final Predicate albumTitlePredicate = criteriaBuilder
                                .like(criteriaBuilder
                                        .lower(root.get("title")), "%" + album.getTitle().toLowerCase() + "%");
                        predicates.add(albumTitlePredicate);
                    }
                    if(!album.getSongs().isEmpty()){
                        for (Song song:album.getSongs()) {
                            final Predicate songPredicate = criteriaBuilder
                                    .like(criteriaBuilder
                                            .lower(songJoin.get("title")), "%" + song.getTitle().toLowerCase() + "%");
                            predicates.add(songPredicate);
                        }
                    }
                }
                criteriaQuery.distinct(true);
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
