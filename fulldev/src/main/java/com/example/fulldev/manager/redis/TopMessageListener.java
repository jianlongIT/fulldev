package com.example.fulldev.manager.redis;

import com.example.fulldev.bo.OrderMessageBO;
import com.example.fulldev.service.CouponBackService;
import com.example.fulldev.service.OrderCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TopMessageListener implements MessageListener {

    @Autowired
    private OrderCancelService orderCancelService;

    @Autowired
    private CouponBackService couponBackService;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        Lock lock = new ReentrantLock();
        lock.lock();

        byte[] body = message.getBody();

        byte[] channel = message.getChannel();

        String topic = new String(channel);
        String expirekey = new String(body);

        OrderMessageBO orderMessageBO = new OrderMessageBO(expirekey);
        orderCancelService.cancel(orderMessageBO);
        couponBackService.returnBack(orderMessageBO);
    }
}
