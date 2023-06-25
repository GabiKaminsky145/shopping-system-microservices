package com.orderservice.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orderservice.Models.DTO.OrderRequestDTO;
import com.orderservice.Services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/api/order")
@RequiredArgsConstructor
public class GatewayController {

    private final OrderService orderService;

    @PostMapping(path = "/placeOrder")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackGatewayError")
    @TimeLimiter(name = "inventory", fallbackMethod = "fallbackTimeLimiterError")
    @Retry(name = "inventory")
    public CompletableFuture<ResponseEntity<?>> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws JsonProcessingException {

        if (orderService.placeOrder(orderRequestDTO)) {
            return CompletableFuture.supplyAsync(() -> new ResponseEntity<>("Order created" ,HttpStatus.CREATED));
        }
        else{
            return CompletableFuture.supplyAsync(() -> new ResponseEntity<>("Unable to complete order" ,HttpStatus.NOT_ACCEPTABLE));
        }
    }

    private CompletableFuture<ResponseEntity<?>> fallbackGatewayError(OrderRequestDTO orderRequestDTO, RuntimeException runtimeException){

        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>((HttpStatus.BAD_GATEWAY)) );
    }

    private CompletableFuture<ResponseEntity<?>> fallbackTimeLimiterError(OrderRequestDTO orderRequestDTO, RuntimeException runtimeException){

        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>((HttpStatus.GATEWAY_TIMEOUT)) );
    }


}
