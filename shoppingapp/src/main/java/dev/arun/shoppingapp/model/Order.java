package dev.arun.shoppingapp.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    private String orderId;
    
    private Date orderedDate;

    private Long userId;
    private int quantity;
    @Column(columnDefinition = "double default 0")
    private double amount;
    private String coupon;
     
}