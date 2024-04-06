package dev.arun.shoppingapp.service;

 

import dev.arun.shoppingapp.model.Inventory;
import dev.arun.shoppingapp.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    public void updateInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }
    
    public  Inventory getProductById(long productId){
    	return inventoryRepository.findByProductId(productId);
    }
}
