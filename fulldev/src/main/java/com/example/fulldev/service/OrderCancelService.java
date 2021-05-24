package com.example.fulldev.service;

import com.example.fulldev.bo.OrderMessageBO;
import com.example.fulldev.model.Order;
import com.example.fulldev.repository.OrderRepository;
import com.example.fulldev.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderCancelService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Transactional
    public void cancel(OrderMessageBO orderMessageBO) {
        if (orderMessageBO.getOrderId() <= 0) {
            throw new RuntimeException("为获取订单");

        }
        this.cancel(orderMessageBO.getOrderId());

    }

    private void cancel(Long oid) {
        Optional<Order> optionalOrder = orderRepository.findById(oid);
        Order order;
        try {
            order = optionalOrder.get();
            int res = orderRepository.cancelOrder(order.getId());
            if (res != 1) {
                return;
            }

            order.getSnapItems().forEach(i -> {

                skuRepository.recoverStock(i.getId(), i.getCount().longValue());
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }
}
