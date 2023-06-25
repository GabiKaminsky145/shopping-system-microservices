package com.orderservice.Services;

import com.orderservice.Models.DTO.OrderRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateOrderEventService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewTopic topic;

    public void createOrderByEvent(OrderRequestDTO orderRequestDTO) {

        try {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic.name(), orderRequestDTO.getRequestedDate().toString());
                 future.whenComplete((result, throwable) -> log.info(result.toString()));

            } catch (Exception ex) {
            Thread.currentThread().interrupt();
            log.error("Error while sending message to Kafka" + ex.getMessage());
            throw new RuntimeException("Error while sending message to Kafka", ex);
        }
    }
}
