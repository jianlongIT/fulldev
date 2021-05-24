package com.example.fulldev.model;

import javax.persistence.*;

@Entity
@Table(name = "spu_img", schema = "sleeve", catalog = "")
public class SpuImg extends BaseEntity {
    @Id
    private Long id;
    private String img;
    private Long spuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }
}
