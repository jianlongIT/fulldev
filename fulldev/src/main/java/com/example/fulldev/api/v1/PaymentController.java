package com.example.fulldev.api.v1;

import com.example.fulldev.core.interceptors.ScopeLevel;
import com.example.fulldev.lib.LinWxNotify;
import com.example.fulldev.service.WxPaymentNotifyService;
import com.example.fulldev.service.WxPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@Validated
public class PaymentController {

    @Autowired
    private WxPaymentService wxPaymentService;

    @Autowired
    private WxPaymentNotifyService wxPaymentNotifyService;

    @RequestMapping("/pay/order/{id}")
    @ScopeLevel
    public Map<String, String> preWxOrder(@PathVariable(name = "id") @Positive Long oid) {

        Map<String, String> miniPayparams = wxPaymentService.preOrder(oid);
        return miniPayparams;
    }

    @RequestMapping("wx/notify")
    public String payCallBack(HttpServletRequest request, HttpServletResponse response) {
        InputStream s = null;
        try {
            s = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return LinWxNotify.fail();
        }

        String xml = LinWxNotify.readNotify(s);
        try {
            wxPaymentNotifyService.processPayNotify(xml);
        } catch (Exception e) {
            e.printStackTrace();
            return LinWxNotify.fail();
        }
        return LinWxNotify.success();
    }
}
