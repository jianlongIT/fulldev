package com.example.fulldev.vo;

public class ThemePureVO {
    private Long id;
    private String title;
    private String description;
    private String name;
    private String entranceImg;
    private String extend;
    private String internalTopImg;
    private String titleImg;
    private String tplName;
    private Boolean online;

    public ThemePureVO() {
    }

    public ThemePureVO(Long id, String title, String description, String name, String entranceImg, String extend, String internalTopImg, String titleImg, String tplName, Boolean online) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.name = name;
        this.entranceImg = entranceImg;
        this.extend = extend;
        this.internalTopImg = internalTopImg;
        this.titleImg = titleImg;
        this.tplName = tplName;
        this.online = online;
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

    public String getEntranceImg() {
        return entranceImg;
    }

    public void setEntranceImg(String entranceImg) {
        this.entranceImg = entranceImg;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getInternalTopImg() {
        return internalTopImg;
    }

    public void setInternalTopImg(String internalTopImg) {
        this.internalTopImg = internalTopImg;
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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}
