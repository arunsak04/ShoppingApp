package dev.arun.shoppingapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.arun.shoppingapp.model.Coupon;
import dev.arun.shoppingapp.repository.CouponRepository;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Autowired
    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Optional<Coupon> getCouponById(Long id) {
        return couponRepository.findById(id);
    }

    public Coupon saveCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }
    
 // Method to get discount by coupon name
    public int getDiscountByCouponName(String couponCode) {
    		 return couponRepository.getDiscountByCouponName(couponCode);
    }
    
    public boolean existByCouponCode(String couponCode) {
    	return couponRepository.existsByCode(couponCode);
    }
  
    }


