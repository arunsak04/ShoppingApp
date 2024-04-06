package dev.arun.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.arun.shoppingapp.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    
	
	 // Custom method to get discount by coupon name
    @Query("SELECT c.discount FROM Coupon c WHERE c.code = :couponName")
    int getDiscountByCouponName(@Param("couponName") String couponName);
    
    // Method to check if a coupon exists by its code
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Coupon c WHERE c.code = :couponCode")
    boolean existsByCode(String couponCode);
}