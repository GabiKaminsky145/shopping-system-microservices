package com.orderservice.Services;

import com.orderservice.Helpers.Mappers.OrderMappers;
import com.orderservice.Models.DTO.OrderRequestDTO;
import com.orderservice.Models.Order;
import com.orderservice.Repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCreateBySyncService {

    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancerClient;
    private final OrderRepository orderRepository;
    private final OrderMappers orderItemMappers;

    public boolean createOrder(OrderRequestDTO orderRequestDTO) {

        ServiceInstance serviceinstance = loadBalancerClient.choose("inventory-service");
        String uri = "http://"+ serviceinstance.getHost() + ":" + serviceinstance.getPort() + "/api/inventory/";

        RequestEntity<?> request = RequestEntity.post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .body(orderRequestDTO.getItemsList());

        try {
            restTemplate.exchange(request, ResponseEntity.class);
            Order order = orderItemMappers.map(orderRequestDTO);
            orderRepository.save(order);
            log.info("order completed with number {}", order.getOrderNumber());
            return true;
        } catch (HttpClientErrorException exception){
            log.error(exception.getMessage(), exception.getStatusCode());
            return false;
        }
    }
}
