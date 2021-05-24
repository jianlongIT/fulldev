package com.example.fulldev.service;

import com.example.fulldev.dto.enumeration.OrderStatus;
import com.example.fulldev.model.Order;
import com.example.fulldev.repository.OrderRepository;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxPaymentNotifyService {

    @Autowired
    private WxPaymentService wxPaymentService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void processPayNotify(String data) {
        Map<String, String> dataMap = new HashMap<>();
        try {
            dataMap = WXPayUtil.xmlToMap(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WXPay wxPay = wxPaymentService.assembleWxPayConfig();
        Boolean valid = true;
        try {
            wxPay.isResponseSignatureValid(dataMap);
            if (!valid) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String returnCode = dataMap.get("retuen_code");
        String orderNo = dataMap.get("out_trade_no");
        String resultCode = dataMap.get("result_code");
        if (!returnCode.equals("SUCCESS")) {
            throw new RuntimeException();
        }
        if (!resultCode.equals("SUCCESS")) {
            throw new RuntimeException();
        }
        if (StringUtils.isEmpty(orderNo)) {

        }

        this.del(orderNo);

    }

    private void del(String orderNo) {
        Optional<Order> optionalOrder = orderRepository.findFirstByOrderNo(orderNo);
        Order order = optionalOrder.orElseThrow(() -> new RuntimeException());

        int res = -1;
        if (order.getStatus().equals(OrderStatus.CANCELED.value()) || order.getStatus().equals(OrderStatus.UNPAID.value())) {
            //更新数据库订单状态
            res = this.orderRepository.updateStatusByOrderNo(orderNo, OrderStatus.PAID.value());
        }
        if (res != 1) {
            throw new RuntimeException();
        }

    }
}
