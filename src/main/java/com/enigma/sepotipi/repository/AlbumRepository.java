package com.enigma.sepotipi.repository;

import com.enigma.sepotipi.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String>, JpaSpecificationExecutor<Album> {
}
