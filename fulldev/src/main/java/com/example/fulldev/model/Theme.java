package com.example.fulldev.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Where(clause = "delete_time is null")
public class Theme extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private String description;
    private String name;
    private String extend;
    private String entranceImg;
    private String internalTopImg;
    private Boolean online;
    private String titleImg;
    private String tplName;

    public Theme() {
    }

    public Theme(Long id, String title, String description, String name, String extend, String entranceImg, String internalTopImg, Boolean online, String titleImg, String tplName, List<Spu> spuList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.name = name;
        this.extend = extend;
        this.entranceImg = entranceImg;
        this.internalTopImg = internalTopImg;
        this.online = online;
        this.titleImg = titleImg;
        this.tplName = tplName;
        this.spuList = spuList;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
    }

    public List<Spu> getSpuList() {
        return spuList;
    }

    public void setSpuList(List<Spu> spuList) {
        this.spuList = spuList;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "theme_spu", joinColumns = @JoinColumn(name = "theme_id")
            , inverseJoinColumns = @JoinColumn(name = "spu_id"))
    private List<Spu> spuList;
}
