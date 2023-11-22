package com.example.saywhonow_backend.repository;

import java.util.List;
import java.util.Optional;

import com.example.saywhonow_backend.models.Role;
import com.example.saywhonow_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// this will take care of querying the database to check if user exists
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    List<User> findAll();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // @Query("SELECT urj.role_id FROM user_role_junction urj WHERE urj.user_id = :userId")
    // List<Role> findRolesbyUserId(@Param("userId") Integer userId);

    // List<User_Role_Junction> findRolesByUser(User user);
}
