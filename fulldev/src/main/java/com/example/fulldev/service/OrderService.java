package com.example.fulldev.service;

import com.example.fulldev.core.LocalUser;
import com.example.fulldev.core.money.IMoneyDiscount;
import com.example.fulldev.dto.OrderDTO;
import com.example.fulldev.dto.SkuInfoDTO;
import com.example.fulldev.dto.enumeration.OrderStatus;
import com.example.fulldev.exception.NotFoundException;
import com.example.fulldev.exception.ParameterException;
import com.example.fulldev.logic.CouponChecker;
import com.example.fulldev.logic.OrderChecker;
import com.example.fulldev.model.*;
import com.example.fulldev.repository.CouponRepository;
import com.example.fulldev.repository.OrderRepository;
import com.example.fulldev.repository.SkuRepository;
import com.example.fulldev.repository.UserCouponRepository;
import com.example.fulldev.util.CommonUtil;
import com.example.fulldev.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private SkuService skuService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private IMoneyDiscount iMoneyDiscount;

    @Value("${order.max-sku-limit}")
    private Integer maxSkuLimit;

    @Value("${order.max-sku-paytime-limit}")
    private Integer payTimeLimit;

    public Page<Order> getUnpaid(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        Date now = new Date();
        return this.orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId(now, OrderStatus.UNPAID.value(), uid, pageable);
    }

    public Page<Order> getOrderByStatus(Integer status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        if (status == OrderStatus.All.value()) {
            return orderRepository.findByUserId(uid, pageable);
        }
        return orderRepository.findByUserIdAndStatus(uid, status, pageable);

    }

    public Optional<Order> getOrderDetail(Long oid) {
        Long uid = LocalUser.getUser().getId();
        return orderRepository.findFirstByUserIdAndId(uid, oid);
    }

    private void writeOffCoupon(Long couponId, Long oid, Long uid) {
        int result = userCouponRepository.writeOff(couponId, oid, uid);
        if (result != 1) {
            throw new ParameterException(50003);
        }
    }

    private void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        orderSkuList.forEach(orderSku -> {
            int result = this.skuRepository.reduceStock(orderSku.getId(), orderSku.getCount().longValue());
            if (result != 1) {
                throw new ParameterException(50003);
            }

        });

    }

    public void updateOrderPrePayid(Long OrderId, String prePayId) {
        Optional<Order> order = orderRepository.findById(OrderId);
        order.ifPresent(o -> {
            o.setPrepayId(prePayId);
            orderRepository.save(o);
        });
        order.orElseThrow(() -> new ParameterException(10007));
    }

    @Transactional
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        String orderNo = OrderUtil.makeOrderNo();

        Calendar now = Calendar.getInstance();
        Calendar currentNow = (Calendar) now.clone();

        Date expireredTime = CommonUtil.addSomeSeconds(now, this.payTimeLimit).getTime();


        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setFinalTotalPrice(orderDTO.getFinalTotalPrice());
        order.setUserId(uid);
        order.setTotalCount(orderChecker.getTotalCount().longValue());
        order.setSnapImg(orderChecker.getLeaderImg());
        order.setSnapTitle(orderChecker.getLeaderTitle());
        order.setStatus(OrderStatus.UNPAID.value());
        order.setExpiredTime(expireredTime);
        order.setPlacedTime((Timestamp) currentNow.getTime());

        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());

        this.orderRepository.save(order);
        this.reduceStock(orderChecker);
        Long couponId = -1L;
        if (orderDTO.getCouponId() != null) {
            this.writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
            couponId = orderDTO.getCouponId();
        }
        this.sendToRedis(order.getId(), uid, couponId);


        return order.getId();

    }

    private void sendToRedis(Long uid, Long oid, Long couponId) {

        try {
            String key = uid.toString() + "," + oid.toString() + "," + couponId.toString();
            String value = "1";
            stringRedisTemplate.opsForValue().set(key, value, payTimeLimit, TimeUnit.SECONDS);
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    public OrderChecker isOk(Long uid, OrderDTO orderDTO) {
        if (orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) < 0) {
            throw new ParameterException(50011);
        }
        List<Long> skuIdList = orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());
        List<Sku> skuList = skuService.getSkuListByOds(skuIdList);
        Long couponId = orderDTO.getCouponId();
        CouponChecker couponChecker = null;
        if (null != couponId) {
            Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException(40005));
            UserCoupon userCoupon = userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId).orElseThrow(() ->
                    new NotFoundException(50006));
            couponChecker = new CouponChecker(coupon, iMoneyDiscount);
        }

        OrderChecker orderChecker = new OrderChecker(orderDTO, skuList, couponChecker, maxSkuLimit);


        return orderChecker;
    }
}
