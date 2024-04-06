package dev.arun.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.arun.shoppingapp.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Add custom methods if needed
}
