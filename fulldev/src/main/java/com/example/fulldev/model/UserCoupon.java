package com.example.fulldev.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_coupon", schema = "sleeve", catalog = "")
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long couponId;
    private Integer status;
    private Date createTime;
    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public UserCoupon() {
    }

    public UserCoupon(Long userId, Long couponId, Integer status, Date createTime, Long orderId) {
        this.userId = userId;
        this.couponId = couponId;
        this.status = status;
        this.createTime = createTime;
        this.orderId = orderId;
    }
}
