package com.example.fulldev.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Banner extends BaseEntity{
    @Id
    private Long id;
    private String name;
    private String description;
    private String title;
    private String img;
    @OneToMany
    @JoinColumn(name = "bannerId")
    private List<BannerItem> items;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<BannerItem> getItems() {
        return items;
    }

    public void setItems(List<BannerItem> items) {
        this.items = items;
    }
}
