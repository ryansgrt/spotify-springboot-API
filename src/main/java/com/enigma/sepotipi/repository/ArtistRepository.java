package com.enigma.sepotipi.repository;

import com.enigma.sepotipi.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, String >, JpaSpecificationExecutor<Artist> {
}
