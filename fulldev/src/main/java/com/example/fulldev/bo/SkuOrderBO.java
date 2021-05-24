package com.example.fulldev.bo;

import com.example.fulldev.dto.SkuInfoDTO;
import com.example.fulldev.model.Sku;

import java.math.BigDecimal;

public class SkuOrderBO {

    private BigDecimal price;
    private Integer count;
    private Long categoryid;

    public BigDecimal getTotalPrice(){
        return this.price.multiply(BigDecimal.valueOf(this.count));
    }

    public SkuOrderBO(Sku sku, SkuInfoDTO skuInfoDTO) {
        this.price = sku.getActualPrice();
        this.categoryid = sku.getCategoryId();
        this.count = skuInfoDTO.getCount();
    }

    public Long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SkuOrderBO(BigDecimal price, Integer count, Long categoryid) {
        this.price = price;
        this.categoryid = categoryid;
        this.count = count;
    }
}
