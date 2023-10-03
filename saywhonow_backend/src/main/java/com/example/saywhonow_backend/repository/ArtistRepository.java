package com.example.saywhonow_backend.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.example.saywhonow_backend.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer>{
    Optional<Artist> findByName(String name);

    Optional<Artist> findById(Integer id);

}
