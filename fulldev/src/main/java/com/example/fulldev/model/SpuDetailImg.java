package com.example.fulldev.model;

import javax.persistence.*;

@Entity
@Table(name = "spu_detail_img", schema = "sleeve", catalog = "")
public class SpuDetailImg extends BaseEntity{
    @Id
    private Long id;
    private String img;
    private Long spuId;
    private Long index;

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

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }
}
