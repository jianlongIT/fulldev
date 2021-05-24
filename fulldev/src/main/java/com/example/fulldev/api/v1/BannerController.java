package com.example.fulldev.api.v1;

import com.example.fulldev.core.interceptors.ScopeLevel;
import com.example.fulldev.entity.Order;
import com.example.fulldev.exception.NotFoundException;
import com.example.fulldev.model.Banner;
import com.example.fulldev.producer.OrderSender;
import com.example.fulldev.service.BannerServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;


@RestController
@RequestMapping("banner")
@Validated
public class BannerController<T> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private BannerServise bannerServise;

    @Autowired
    private OrderSender orderSender;

    @RequestMapping("test")
    public Banner test(@RequestBody Map data) {
        System.out.println((String) data.get("name"));
        Banner banner = bannerServise.getByName((String) data.get("name"));
        return banner;
    }

    @ScopeLevel(8)
    @GetMapping("/name/{name}")
    public Banner getByName(@PathVariable String name) {
        Banner banner = bannerServise.getByName(name);
        if (null == banner) {
            throw new NotFoundException(30005);
        }
        return banner;
    }

    @RequestMapping("/send")
    public void send() {
        Order order = new Order();
        order.setId(20200211);
        order.setName("Jianlong");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        Map properties = new HashMap();
        properties.put("number", 111);
        try {
            orderSender.sendOrder(order, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/settoken")
    public void settoken(HttpSession httpSession) {
        httpSession.setAttribute("token", UUID.randomUUID().toString());
    }

    @RequestMapping("/gettoken")
    public String gettoken(HttpSession httpSession) {
        System.out.println(httpSession.getAttribute("token").toString());
        return httpSession.getAttribute("token").toString();
    }
}
