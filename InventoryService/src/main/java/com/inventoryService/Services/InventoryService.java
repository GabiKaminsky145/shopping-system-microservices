package com.inventoryService.Services;

import com.inventoryService.Models.Inventory.Inventory;
import com.inventoryService.Repositories.InventoryRepository;
import com.orderservice.Models.DTO.OrderItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    @Transactional
    public boolean updateStockIfOrderExist(List<OrderItemDTO> orderItems) {

        List<Inventory> newInventories = new ArrayList<>();

        for (OrderItemDTO orderItem: orderItems) {
            Optional<Inventory> inventory = inventoryRepository.findBySkuCode(orderItem.getSkuCode());
            if (inventory.isEmpty() || inventory.get().getQuantity() < orderItem.getQuantity()){
                return false;
            }
            else{
                Inventory currInventory = inventory.get();
                currInventory.setQuantity(currInventory.getQuantity() - orderItem.getQuantity());
                newInventories.add(currInventory);
            }
        }

        inventoryRepository.saveAllAndFlush(newInventories);
        return true;
    }

    public List<Inventory> getInventoryList() {

        return inventoryRepository.findAll();
    }
}
