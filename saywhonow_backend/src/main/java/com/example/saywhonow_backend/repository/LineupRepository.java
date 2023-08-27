package com.example.saywhonow_backend.repository;

import java.util.Optional;

import com.example.saywhonow_backend.domain.Lineup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface LineupRepository extends JpaRepository<Lineup, Integer> {
    Optional<Lineup> findById(Integer id);

    Lineup findByFestival(String festival);
}
