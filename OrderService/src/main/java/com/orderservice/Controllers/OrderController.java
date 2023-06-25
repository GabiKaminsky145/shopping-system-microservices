package com.orderservice.Controllers;

import com.orderservice.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(path = "/orderList")
    public ResponseEntity<?> getAllOrders(){

        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/orderList")
    public ResponseEntity<?> deleteAllOrders(){

        orderService.deleteAllOrders();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
