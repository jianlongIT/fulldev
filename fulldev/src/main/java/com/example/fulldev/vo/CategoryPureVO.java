package com.example.fulldev.vo;

import com.example.fulldev.model.Category;
import org.springframework.beans.BeanUtils;

public class CategoryPureVO {
    private Long id;

    private String name;

    private String description;

    private Boolean isRoot;

    private String img;

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

    private Long parentId;

    private Long index;

    public CategoryPureVO(Category category) {
        BeanUtils.copyProperties(category, this);
        ;
    }

}
