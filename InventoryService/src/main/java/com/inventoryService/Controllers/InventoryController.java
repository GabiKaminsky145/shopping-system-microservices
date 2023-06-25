package com.inventoryService.Controllers;

import com.inventoryService.Services.InventoryService;
import com.orderservice.Models.DTO.OrderItemDTO;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    private Tracer tracer;

    @PostMapping(path = "/")
    public ResponseEntity<?> updateStock(@RequestBody List<OrderItemDTO> orderItems){

        if (inventoryService.updateStockIfOrderExist(orderItems)) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("order not completed" ,HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> getInventoryList(){

        return new ResponseEntity<>(inventoryService.getInventoryList() ,HttpStatus.OK);
    }

}
