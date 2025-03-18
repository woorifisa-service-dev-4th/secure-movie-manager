package dev.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.auth.model.User;


public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);
}
