package com.example.saywhonow_backend.repository;

import com.example.saywhonow_backend.Role;
import com.google.common.base.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


@Respositoy
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByAuthority(String authority);
}
