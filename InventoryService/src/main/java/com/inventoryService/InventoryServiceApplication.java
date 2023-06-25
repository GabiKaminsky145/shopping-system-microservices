package com.inventoryService;

import com.inventoryService.Models.Inventory.Inventory;
import com.inventoryService.Repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {
    public static void main(String[] args) {

        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(InventoryRepository inventoryRepository){
        return args -> {

            Inventory inventory = new Inventory();
            inventory.setSkuCode("gabi");
            inventory.setQuantity(12);

            Inventory inventory1 = new Inventory();
            inventory1.setSkuCode("ortal");
            inventory1.setQuantity(728);

            inventoryRepository.save(inventory1);
            inventoryRepository.save(inventory);
        };
    }

}
