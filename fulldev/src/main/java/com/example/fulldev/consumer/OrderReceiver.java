package com.example.fulldev.consumer;


import com.example.fulldev.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OrderReceiver {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue", durable = "true"),
            exchange = @Exchange(name = "order-exchange", durable = "true", type = "topic",ignoreDeclarationExceptions = "true"),
            key = "order.*"
    )
    )
    @RabbitHandler
    public void onOrderMEssage(@Payload Order order, @Headers Map<String, Object> headers, Channel channel) {
        System.out.println("消费端接收");
        System.out.println(order.getId());
        System.out.println(order.getMessageId());
        System.out.println(order.getName());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
