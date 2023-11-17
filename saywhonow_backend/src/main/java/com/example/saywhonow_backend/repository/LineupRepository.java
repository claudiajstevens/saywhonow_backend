package com.example.saywhonow_backend.repository;

import java.util.Optional;

import com.example.saywhonow_backend.domain.Lineup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface LineupRepository extends JpaRepository<Lineup, Integer> {
    Optional<Lineup> findById(Integer id);

    Lineup findByFestival(String festival);

    List<Lineup> findByFestivalId(Integer festivalId);

    @Query("SELECT l FROM Lineup l WHERE l.endDate >= CURRENT_DATE")
    List<Lineup> findUpcomingLineups();

}
