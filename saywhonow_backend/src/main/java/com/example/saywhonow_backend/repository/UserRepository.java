package com.example.saywhonow_backend.repository;

import com.example.saywhonow_backend.domain.User;
import com.google.common.base.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// this will take care of querying the database to check if user exists
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
