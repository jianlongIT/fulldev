package com.example.fulldev.service;

import com.example.fulldev.core.LocalUser;
import com.example.fulldev.exception.NotFoundException;
import com.example.fulldev.exception.ParameterException;
import com.example.fulldev.model.Order;
import com.example.fulldev.repository.OrderRepository;
import com.example.fulldev.util.CommonUtil;
import com.example.fulldev.util.HttpRequestProxy;
import com.github.wxpay.sdk.LinWxPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxPaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    private static LinWxPayConfig linWxPayConfig = new LinWxPayConfig();

    public Map<String, String> preOrder(Long oid) {
        Long uid = LocalUser.getUser().getId();
        Optional<Order> optionalOrder = orderRepository.findFirstByUserIdAndId(uid, oid);
        Order order = optionalOrder.orElseThrow(() ->
                new NotFoundException(50009)
        );
        if (order.needCancel()) {
            throw new ParameterException(50010);
        }
        WXPay wxPay = this.assembleWxPayConfig();
        Map params = makePreOrderParams(order.getFinalTotalPrice(), order.getOrderNo());
        Map<String, String> wxOrder = null;
        try {
            wxOrder = wxPay.unifiedOrder(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!wxOrder.get("return_code").equals("SUCCESS") || !wxOrder.get("result_code").equals("SUCCESS")) {
            this.orderService.updateOrderPrePayid(order.getId(), wxOrder.get("prepay_id"));
        }

        return makePaySignature(wxOrder);
    }

    private Map<String, String> makePaySignature(Map<String, String> wxOrder) {
        String packages = "prepay_id=" + wxOrder.get("prepay_id");
        Map wxPayMap = new HashMap();
        wxPayMap.put("appId", WxPaymentService.linWxPayConfig.getAppID());
        wxPayMap.put("timeStamp", CommonUtil.timeStamp10());
        wxPayMap.put("nonceStr", RandomStringUtils.randomAlphabetic(32));
        wxPayMap.put("packages", packages);
        wxPayMap.put("signType", "HMAC-SHA256");

        String sign;
        try {
            sign = WXPayUtil.generateSignature(wxPayMap, WxPaymentService.linWxPayConfig.getKey(), WXPayConstants.SignType.HMACSHA256);
            wxPayMap.put("paySign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        wxPayMap.remove("appId");
        return wxPayMap;

    }


    private Map makePreOrderParams(BigDecimal serverFinalPrice, String OrderNo) {
        Map data = new HashMap();
        data.put("body", "sleeve");
        data.put("out_trade_no", OrderNo);
        data.put("device_info", "sleeve");
        data.put("fee_type", "CHY");
        data.put("trade_type", "JSAPI");

        data.put("total_fee", CommonUtil.yuanToFenPlainString(serverFinalPrice));
        data.put("openid", LocalUser.getUser().getOpenid());
        data.put("spbill_create_ip", HttpRequestProxy.getRemoteRealIp());
        data.put("notify_url", "");//回调消息推送地址
        return data;
    }

    public WXPay assembleWxPayConfig() {
        WXPay wxPay = null;
        try {
            wxPay = new WXPay(WxPaymentService.linWxPayConfig);
        } catch (Exception e) {
            throw new ServerErrorException("9999");
        }
        return wxPay;

    }
}
