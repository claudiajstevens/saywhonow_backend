package com.example.saywhonow_backend.repository;

import java.util.Optional;

import com.example.saywhonow_backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByAuthority(String authority);
