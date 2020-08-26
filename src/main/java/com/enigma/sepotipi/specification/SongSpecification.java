package com.enigma.sepotipi.specification;

import com.enigma.sepotipi.entity.Album;
import com.enigma.sepotipi.entity.Artist;
import com.enigma.sepotipi.entity.Genre;
import com.enigma.sepotipi.entity.Song;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;

public class SongSpecification {

    public static Specification<Song> findByCriteria(Song song){
        return new Specification<Song>() {
            @Override
            public Predicate toPredicate(Root<Song> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                final Collection<Predicate> predicates = new ArrayList<>();
                final Join<Song, Artist> artistJoin = root.join("artist", JoinType.INNER);
                final Join<Song, Album> albumJoin = root.join("album", JoinType.INNER);
                final Join<Song, Genre> genreJoin = root.join("genre", JoinType.INNER);
                if(song!=null){
                    if(!StringUtils.isEmpty(song.getTitle())){
                        final Predicate songTitlePredicate = criteriaBuilder
                                .like(criteriaBuilder
                                        .lower(root.get("title")), "%" + song.getTitle().toLowerCase() + "%");
                        predicates.add(songTitlePredicate);
                    }
                    if(song.getArtist()!=null){
                        final Predicate songArtistPredicate = criteriaBuilder
                                .like(criteriaBuilder.lower(artistJoin.get("name")), "%" + song.getArtist().getName().toLowerCase() + "%");
                        predicates.add(songArtistPredicate);
                    }
                    if(song.getAlbum()!=null){
                        final Predicate songAlbumPredicate = criteriaBuilder
                                .like(criteriaBuilder.lower(albumJoin.get("title")), "%" + song.getAlbum().getTitle().toLowerCase() + "%");
                        predicates.add(songAlbumPredicate);
                    }
                    if(song.getGenre()!=null){
                        final Predicate songGenrePredicate = criteriaBuilder
                                .like(criteriaBuilder.lower(genreJoin.get("genre")), "%" + song.getGenre().getName().toLowerCase() + "%");
                        predicates.add(songGenrePredicate);
                    }
                }
                criteriaQuery.distinct(true);
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
