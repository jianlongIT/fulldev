package com.example.fulldev.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Activity extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private String remark;
    private Boolean online;
    private String entranceImg;
    private String internalTopImg;
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "activityId")
    private List<Coupon> couponList;

    public Activity(Long id, String title, String description, Timestamp startTime, Timestamp endTime, String remark, Boolean online, String entranceImg, String internalTopImg, String name, List<Coupon> couponList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remark = remark;
        this.online = online;
        this.entranceImg = entranceImg;
        this.internalTopImg = internalTopImg;
        this.name = name;
        this.couponList = couponList;
    }

    public Activity() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getEntranceImg() {
        return entranceImg;
    }

    public void setEntranceImg(String entranceImg) {
        this.entranceImg = entranceImg;
    }

    public String getInternalTopImg() {
        return internalTopImg;
    }

    public void setInternalTopImg(String internalTopImg) {
        this.internalTopImg = internalTopImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }
}
