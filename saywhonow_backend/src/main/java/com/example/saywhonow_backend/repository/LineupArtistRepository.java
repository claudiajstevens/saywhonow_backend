package com.example.saywhonow_backend.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.saywhonow_backend.domain.LineupArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineupArtistRepository extends JpaRepository<LineupArtist, Integer> {
    Optional<LineupArtist> findByArtistName(String artistName);

    Optional<LineupArtist> findById(Integer id);

    List<LineupArtist> findByDay(Date day);

    List<LineupArtist> findByLineupId(Integer lineupId);
}
