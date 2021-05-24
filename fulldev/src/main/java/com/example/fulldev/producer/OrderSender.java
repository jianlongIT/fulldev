package com.example.fulldev.producer;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class OrderSender {
    @Autowired
    private RabbitTemplate template;



    public void sendOrder(Object order, Map<String,Object> properties) throws Exception {
        MessageHeaders messageHeaders=new MessageHeaders(properties);
        Message msg=MessageBuilder.createMessage(order,messageHeaders);
        template.setConfirmCallback((correlationData, ack, cause) -> {
            System.err.println("correlationData:" + ack);
            System.err.println("ack:" + ack);
            if (!ack) {
                System.out.println("异常处理");
            }
        });
        template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("========returnCallback=============");
            System.out.println("========returnCallback=============");
        });
        template.convertAndSend("order-exchange","order.*",msg);

    }
}
