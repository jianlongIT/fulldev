package com.example.fulldev.logic;

import com.example.fulldev.bo.SkuOrderBO;
import com.example.fulldev.dto.OrderDTO;
import com.example.fulldev.dto.SkuInfoDTO;
import com.example.fulldev.exception.ParameterException;
import com.example.fulldev.model.OrderSku;
import com.example.fulldev.model.Sku;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderChecker {
    private OrderDTO orderDTO;
    private List<Sku> serverSkuList;
    private CouponChecker couponChecker;
    private Integer maxSkuLimit;
    private List<OrderSku> orderSkuList = new ArrayList<>();

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public List<Sku> getServerSkuList() {
        return serverSkuList;
    }

    public void setServerSkuList(List<Sku> serverSkuList) {
        this.serverSkuList = serverSkuList;
    }

    public CouponChecker getCouponChecker() {
        return couponChecker;
    }

    public void setCouponChecker(CouponChecker couponChecker) {
        this.couponChecker = couponChecker;
    }

    public Integer getMaxSkuLimit() {
        return maxSkuLimit;
    }

    public void setMaxSkuLimit(Integer maxSkuLimit) {
        this.maxSkuLimit = maxSkuLimit;
    }

    public List<OrderSku> getOrderSkuList() {
        return orderSkuList;
    }

    public void setOrderSkuList(List<OrderSku> orderSkuList) {
        this.orderSkuList = orderSkuList;
    }

    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList, CouponChecker couponChecker, Integer maxSkuLimit) {
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
        this.maxSkuLimit = maxSkuLimit;
    }

    public String getLeaderImg() {
        return serverSkuList.get(0).getImg();
    }

    public String getLeaderTitle() {
        return serverSkuList.get(0).getTitle();
    }

    public Integer getTotalCount() {
        return this.orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getCount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() <= 0) {
            throw new ParameterException(50007);
        }
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
    }

    private void isOk() {
        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList = new ArrayList<>();
        this.skuNotOnSale(serverSkuList.size(), orderDTO.getSkuInfoList().size());
        for (int i = 0; i < serverSkuList.size(); i++) {
            Sku sku = serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = this.orderDTO.getSkuInfoList().get(i);
            this.containsSoldOutSku(sku);
            this.beyoundSkuStock(sku, skuInfoDTO);
            this.beyoundMaxSkuLimit(skuInfoDTO);
            serverTotalPrice = serverTotalPrice.add(calculateSkuOrderPrice(sku, skuInfoDTO));

            skuOrderBOList.add(new SkuOrderBO(sku, skuInfoDTO));

            this.orderSkuList.add(new OrderSku(sku, skuInfoDTO));
        }
        this.totalProceIsOk(orderDTO.getTotalPrice(), serverTotalPrice);
        if (couponChecker != null) {
            this.couponChecker.isOk();
            this.couponChecker.canBeUsed(skuOrderBOList, serverTotalPrice);
            this.couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(), serverTotalPrice);


        }
    }

    private void totalProceIsOk(BigDecimal orderTotalPrice, BigDecimal serverTotalPrice) {
        if (orderTotalPrice.compareTo(serverTotalPrice) != 0) {
            throw new ParameterException(50008);
        }
    }

    private void skuNotOnSale(int count1, int count2) {
        if (count1 != count2) {
            throw new ParameterException(50002);
        }
    }

    private void containsSoldOutSku(Sku sku) {
        if (sku.getStock() == 0) {
            throw new ParameterException(50001);
        }
    }

    private void beyoundSkuStock(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (sku.getStock() < skuInfoDTO.getCount()) {
            throw new ParameterException(50003);
        }
    }

    private void beyoundMaxSkuLimit(SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > this.maxSkuLimit) {
            throw new ParameterException(50004);
        }
    }
}
