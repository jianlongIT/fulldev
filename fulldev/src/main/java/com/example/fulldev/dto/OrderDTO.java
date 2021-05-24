package com.example.fulldev.dto;

import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;
import java.util.List;

public class OrderDTO {
    @DecimalMax(value = "0.00",message = "不在合法范围内")
    private BigDecimal totalPrice;

    private BigDecimal finalTotalPrice;

    private Long couponId;

    private List<SkuInfoDTO> skuInfoList;

    private OrderAddressDTO address;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getFinalTotalPrice() {
        return finalTotalPrice;
    }

    public void setFinalTotalPrice(BigDecimal finalTotalPrice) {
        this.finalTotalPrice = finalTotalPrice;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public List<SkuInfoDTO> getSkuInfoList() {
        return skuInfoList;
    }

    public void setSkuInfoList(List<SkuInfoDTO> skuInfoList) {
        this.skuInfoList = skuInfoList;
    }

    public OrderAddressDTO getAddress() {
        return address;
    }

    public void setAddress(OrderAddressDTO address) {
        this.address = address;
    }
}
