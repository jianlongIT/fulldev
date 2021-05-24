package com.example.fulldev.vo;

import com.example.fulldev.model.Activity;
import org.springframework.beans.BeanUtils;

public class ActivityPureVO {
    private Long id;
    private String title;
    private String entranceImg;
    private Boolean online;
    private String remark;
    private String startTime;
    private String endTime;

    public ActivityPureVO(Activity activity) {
        BeanUtils.copyProperties(activity, this);
    }

    public ActivityPureVO(Object object) {
        BeanUtils.copyProperties(object, this);
    }

    public ActivityPureVO() {
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

    public String getEntranceImg() {
        return entranceImg;
    }

    public void setEntranceImg(String entranceImg) {
        this.entranceImg = entranceImg;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
