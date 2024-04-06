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
@Table(name = "inventory")
public class Inventory {
	
	// Its like a Stock and inventory
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private long productId;
	
	@Column(columnDefinition = "BIGINT default 0")
	private long orderd;
	
 
	private double price;
	
	private int available;
	
	

}
