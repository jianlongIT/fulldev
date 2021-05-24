package com.example.fulldev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
public class BaseEntity {
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Timestamp createTime;
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Timestamp updateTime;
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Timestamp deleteTime;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Timestamp deleteTime) {
        this.deleteTime = deleteTime;
    }
}
