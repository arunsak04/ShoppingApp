package dev.arun.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.arun.shoppingapp.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Add custom methods if needed
}
