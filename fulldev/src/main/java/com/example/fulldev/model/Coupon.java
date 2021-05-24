package com.example.fulldev.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Coupon extends BaseEntity {
    @Id
    private Long id;
    private Long activityId;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private Integer valitiy;
    private String remark;
    private Boolean wholeStore;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "couponList")
    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Coupon() {
    }

    public Coupon(Long id, Long activityId, String title, Date startTime, Date endTime, String description, BigDecimal fullMoney, BigDecimal minus, BigDecimal rate, Integer type, Integer valitiy, String remark, Boolean wholeStore) {
        this.id = id;
        this.activityId = activityId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.fullMoney = fullMoney;
        this.minus = minus;
        this.rate = rate;
        this.type = type;
        this.valitiy = valitiy;
        this.remark = remark;
        this.wholeStore = wholeStore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
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

    public Integer getValitiy() {
        return valitiy;
    }

    public void setValitiy(Integer valitiy) {
        this.valitiy = valitiy;
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
