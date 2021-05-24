package com.example.fulldev.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Where(clause = "delete_time is null and online = 1")
public class Category extends BaseEntity {
    @Id
    private Long id;

    private String name;

    private String description;

    private Boolean isRoot;

    private String img;

    private Long parentId;

    private Long index;

    public Category() {
    }

    public Category(Long id, String name, String description, Boolean isRoot, String img, Long parentId, Long index) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isRoot = isRoot;
        this.img = img;
        this.parentId = parentId;
        this.index = index;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRoot() {
        return isRoot;
    }

    public void setRoot(Boolean root) {
        isRoot = root;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "coupon_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> couponList;

}
