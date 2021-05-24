package com.example.fulldev.model;

import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Where(clause = "delete_time is null and online = 1")
public class ActivityCover extends BaseEntity {
    @Id
    private Long id;
    private String coverImg;
    private String internalTopImg;
    private String name;
    private String title;
    private String description;
    private Boolean online;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public ActivityCover() {
    }

    public ActivityCover(Long id, String coverImg, String internalTopImg, String name, String title, String description, Boolean online) {
        this.id = id;
        this.coverImg = coverImg;
        this.internalTopImg = internalTopImg;
        this.name = name;
        this.title = title;
        this.description = description;
        this.online = online;
    }
}
