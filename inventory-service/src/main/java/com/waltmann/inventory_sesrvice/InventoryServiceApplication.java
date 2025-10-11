package com.waltmann.inventory_sesrvice;

import com.waltmann.inventory_sesrvice.model.Inventory;
import com.waltmann.inventory_sesrvice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

    @Bean
    public CommandLineRunner init(InventoryRepository inventoryRepository) {
        return args -> {
          Inventory inventory = new Inventory();
          inventory.setSkuCode("iphone_13");
          inventory.setQuantity(1);
          inventoryRepository.save(inventory);

          Inventory inventory1 = new Inventory();
          inventory1.setSkuCode("iphone_13_red");
          inventory1.setQuantity(0);
          inventoryRepository.save(inventory1);
        };
    }
}
