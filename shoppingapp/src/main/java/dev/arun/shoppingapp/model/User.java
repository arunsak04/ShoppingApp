package dev.arun.shoppingapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    private long mobileNo;
    private String address;
    
    //to know how many quantities user ordered and to cross check he is a new USER or NOT,
    // ZERO ordered Quantity means new USER | First Order OFF50
    @Column(columnDefinition = "BIGINT default 0")
    private long orderedQuantity;
    
    // to list how many coupons used till now  (to verify NEW USER)
    @Column(columnDefinition = "BIGINT default 0")
    private long couponsUsed;
    
    
   
}