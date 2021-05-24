package com.example.fulldev.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Where(clause = "delete_time is null and online =1 ")
public class Spu extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private String subtitle;
    private Long categoryId;
    private Long rootCategoryId;
    private Boolean online;
    private String price;
    private Integer sketchSpecId;
    private Integer defaultSkuId;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private Boolean isTest;
    @OneToMany
    @JoinColumn(name = "spuId")
    private List<SpuImg> spuImgList;

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<SpuDetailImg> spuDetailImgList;

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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getRootCategoryId() {
        return rootCategoryId;
    }

    public void setRootCategoryId(Long rootCategoryId) {
        this.rootCategoryId = rootCategoryId;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getSketchSpecId() {
        return sketchSpecId;
    }

    public void setSketchSpecId(Integer sketchSpecId) {
        this.sketchSpecId = sketchSpecId;
    }

    public Integer getDefaultSkuId() {
        return defaultSkuId;
    }

    public void setDefaultSkuId(Integer defaultSkuId) {
        this.defaultSkuId = defaultSkuId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Boolean getTest() {
        return isTest;
    }

    public void setTest(Boolean test) {
        isTest = test;
    }

    public List<SpuImg> getSpuImgList() {
        return spuImgList;
    }

    public void setSpuImgList(List<SpuImg> spuImgList) {
        this.spuImgList = spuImgList;
    }

    public List<SpuDetailImg> getSpuDetailImgList() {
        return spuDetailImgList;
    }

    public void setSpuDetailImgList(List<SpuDetailImg> spuDetailImgList) {
        this.spuDetailImgList = spuDetailImgList;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }

    public String getForThemeImg() {
        return forThemeImg;
    }

    public void setForThemeImg(String forThemeImg) {
        this.forThemeImg = forThemeImg;
    }

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<Sku> skuList;

    //private Object spuThemeImg;
    private String forThemeImg;

}
