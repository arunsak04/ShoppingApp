package dev.arun.shoppingapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.arun.shoppingapp.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	
	// Method to find inventory by product ID using a custom query
	@Query("SELECT i FROM Inventory i WHERE i.productId = :productId")
    Inventory findByProductId(@Param("productId") Long productId);
}
