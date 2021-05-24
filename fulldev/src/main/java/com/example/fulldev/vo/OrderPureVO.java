package com.example.fulldev.vo;

import com.example.fulldev.model.Order;
import org.springframework.beans.BeanUtils;

public class OrderPureVO extends Order {
    private Long period;

    public OrderPureVO(Order order, Long period) {
        BeanUtils.copyProperties(order, this);
        this.period = period;
    }
}
