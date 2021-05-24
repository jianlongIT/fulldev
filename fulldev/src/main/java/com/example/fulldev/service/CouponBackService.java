package com.example.fulldev.service;

import com.example.fulldev.bo.OrderMessageBO;
import com.example.fulldev.dto.enumeration.OrderStatus;
import com.example.fulldev.model.Order;
import com.example.fulldev.repository.OrderRepository;
import com.example.fulldev.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CouponBackService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Transactional
    public void returnBack(OrderMessageBO orderMessageBO) {
        Long oid = orderMessageBO.getOrderId();
        Long uid = orderMessageBO.getUserId();
        long coupoonId = orderMessageBO.getCouponId();

        if (coupoonId == -1) {
            return;
        }

        Optional<Order> optionalOrderr = orderRepository.findFirstByUserIdAndId(uid, oid);

        Order order = optionalOrderr.get();

        if (order != null) {
            if (order.getStatusEnum().equals(OrderStatus.UNPAID)
                    || order.getStatusEnum().equals(OrderStatus.CANCELED)) {
                this.userCouponRepository.returnBack(coupoonId, uid);
            }
        }


    }


}
