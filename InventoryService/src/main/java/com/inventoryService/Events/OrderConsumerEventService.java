package com.inventoryService.Events;


import com.inventoryService.Models.Order.PlaceOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class OrderConsumerEventService {

    @KafkaListener(
            topics = "orderTopic"
            , groupId = "${spring.kafka.consumer.group-id}"
    )
    public void handleNotification(PlaceOrderEvent placeOrderEvent) throws InterruptedException {
        log.info("Got message <{}>", placeOrderEvent);

        handleRequestedDate(LocalDateTime.parse(placeOrderEvent.getRequestedDate()));
    }

    private void handleRequestedDate(LocalDateTime requestedDate) throws InterruptedException {

        while (!requestedDate.isBefore(LocalDateTime.now())) {
            Thread.sleep(200000);
        }
    }

}
