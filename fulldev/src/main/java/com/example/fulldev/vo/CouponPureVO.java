package com.example.fulldev.vo;

import com.example.fulldev.model.Coupon;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CouponPureVO implements Serializable {
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private String remark;
    private Boolean wholeStore;

    public CouponPureVO(Object[] objects) {
        Coupon coupon = (Coupon) objects[0];
        BeanUtils.copyProperties(coupon, this);
    }

    public CouponPureVO(Coupon coupon) {
        BeanUtils.copyProperties(coupon, this);
    }

    public static List<CouponPureVO> getList(List<Coupon> couponList) {
        return couponList.stream()
                .map(CouponPureVO::new)
                .collect(Collectors.toList());
    }

    public CouponPureVO() {
    }

    public CouponPureVO(Long id, String title, Date startTime, Date endTime, String description, BigDecimal fullMoney, BigDecimal minus, BigDecimal rate, Integer type, String remark, Boolean wholeStore) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.fullMoney = fullMoney;
        this.minus = minus;
        this.rate = rate;
        this.type = type;
        this.remark = remark;
        this.wholeStore = wholeStore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getFullMoney() {
        return fullMoney;
    }

    public void setFullMoney(BigDecimal fullMoney) {
        this.fullMoney = fullMoney;
    }

    public BigDecimal getMinus() {
        return minus;
    }

    public void setMinus(BigDecimal minus) {
        this.minus = minus;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getWholeStore() {
        return wholeStore;
    }

    public void setWholeStore(Boolean wholeStore) {
        this.wholeStore = wholeStore;
    }
}
