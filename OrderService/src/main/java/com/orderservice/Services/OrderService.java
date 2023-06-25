package com.orderservice.Services;

import com.orderservice.Helpers.Mappers.OrderMappers;
import com.orderservice.Models.DTO.OrderRequestDTO;
import com.orderservice.Models.DTO.OrderResponseDTO;
import com.orderservice.Repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final CreateOrderEventService createOrderEventService;
    private final OrderCreateBySyncService orderCreateBySyncService;
    private final OrderRepository orderRepository;
    private final OrderMappers orderItemMappers;

    public boolean placeOrder(OrderRequestDTO orderRequestDTO) {

        if (Objects.nonNull(orderRequestDTO.getRequestedDate()) && orderRequestDTO.getRequestedDate().isAfter(LocalDateTime.now())) {
            createOrderEventService.createOrderByEvent(orderRequestDTO);
        }

        return orderCreateBySyncService.createOrder(orderRequestDTO);
    }

    public List<OrderResponseDTO> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(orderItemMappers::map).collect(Collectors.toList());
    }

    public void deleteAllOrders() {

        orderRepository.deleteAll();
    }
}
