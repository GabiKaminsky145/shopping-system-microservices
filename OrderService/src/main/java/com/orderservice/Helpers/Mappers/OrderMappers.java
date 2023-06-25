package com.orderservice.Helpers.Mappers;

import com.orderservice.Models.DTO.OrderItemDTO;
import com.orderservice.Models.DTO.OrderRequestDTO;
import com.orderservice.Models.DTO.OrderResponseDTO;
import com.orderservice.Models.Order;
import com.orderservice.Models.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMappers {

    public Order map(OrderRequestDTO orderRequestDTO) {

        return Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .requestedDate(orderRequestDTO.getRequestedDate())
                .itemsList(orderRequestDTO.getItemsList()
                        .stream()
                        .map(this::map)
                        .collect(Collectors.toList()))
                .build();
    }

    public OrderResponseDTO map(Order order) {

        return OrderResponseDTO.builder()
                .orderNumber(order.getOrderNumber())
                .requestedDate(order.getRequestedDate())
                .itemsList(order.getItemsList()
                        .stream()
                        .map(this::map)
                        .collect(Collectors.toList()))
                .build();
    }

    public List<OrderItem> map(List<OrderItemDTO> orderItemDTOS){

        return orderItemDTOS.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private OrderItem map(OrderItemDTO orderItemDTO){

        return OrderItem.builder()
                .skuCode(orderItemDTO.getSkuCode())
                .price(orderItemDTO.getPrice())
                .quantity(orderItemDTO.getQuantity())
                .build();
    }

    private OrderItemDTO map(OrderItem orderItem){

        return OrderItemDTO.builder()
                .skuCode(orderItem.getSkuCode())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
