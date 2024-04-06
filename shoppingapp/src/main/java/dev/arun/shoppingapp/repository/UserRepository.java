package dev.arun.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.arun.shoppingapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Add custom methods if needed
}
