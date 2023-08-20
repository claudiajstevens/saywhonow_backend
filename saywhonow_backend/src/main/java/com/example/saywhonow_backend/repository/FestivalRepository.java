package com.example.saywhonow_backend.repository;

import java.util.Optional;

import com.example.saywhonow_backend.domain.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Integer> {
    Optional<Festival> findByName(String name);

    Optional<Festival> findById(Integer id);
}
