package com.example.fulldev.model;

import com.example.fulldev.dto.OrderAddressDTO;
import com.example.fulldev.dto.enumeration.OrderStatus;
import com.example.fulldev.util.CommonUtil;
import com.example.fulldev.util.GenericAndJson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Where(clause = "delete_time is null")
@Table(name = "`Order`")
@Entity
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalPrice;
    private Long totalCount;
    private String snapImg;
    private String snapTitle;
    private Date expiredTime;
    private Date placedTime;

    private String snapItems;

    private String snapAddress;

    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Integer status;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getSnapImg() {
        return snapImg;
    }

    public void setSnapImg(String snapImg) {
        this.snapImg = snapImg;
    }

    public String getSnapTitle() {
        return snapTitle;
    }

    public void setSnapTitle(String snapTitle) {
        this.snapTitle = snapTitle;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Date getPlacedTime() {
        return placedTime;
    }

    public void setPlacedTime(Date placedTime) {
        this.placedTime = placedTime;
    }

    public void setSnapItems(String snapItems) {
        this.snapItems = snapItems;
    }

    public void setSnapAddress(String snapAddress) {
        this.snapAddress = snapAddress;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public BigDecimal getFinalTotalPrice() {
        return finalTotalPrice;
    }

    public void setFinalTotalPrice(BigDecimal finalTotalPrice) {
        this.finalTotalPrice = finalTotalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Order(Long id, String orderNo, Long userId, BigDecimal totalPrice, Long totalCount, String snapImg, String snapTitle, Date expiredTime, Date placedTime, String snapItems, String snapAddress, String prepayId, BigDecimal finalTotalPrice, Integer status) {
        this.id = id;
        this.orderNo = orderNo;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
        this.snapImg = snapImg;
        this.snapTitle = snapTitle;
        this.expiredTime = expiredTime;
        this.placedTime = placedTime;
        this.snapItems = snapItems;
        this.snapAddress = snapAddress;
        this.prepayId = prepayId;
        this.finalTotalPrice = finalTotalPrice;
        this.status = status;
    }

    @JsonIgnore
    public OrderStatus getStatusEnum() {
        return OrderStatus.toType(this.status);
    }

    //orders
    public Boolean needCancel() {
        if (!this.getStatusEnum().equals(OrderStatus.UNPAID)) {
            return true;
        }
        boolean isOutOfDate = CommonUtil.isOutOfDate(this.getExpiredTime(),null);
        if (isOutOfDate) {
            return true;
        }
        return false;
    }

    //
    public void setSnapItems(List<OrderSku> orderSkuList) {
        if (orderSkuList.isEmpty()) {
            return;
        }
        this.snapItems = GenericAndJson.objectToJson(orderSkuList);
    }

    public List<OrderSku> getSnapItems() {
        List<OrderSku> list = GenericAndJson.jsonToObject(this.snapItems,
                new TypeReference<List<OrderSku>>() {
                });
        return list;
    }


    public OrderAddressDTO getSnapAddress() {
        if (this.snapAddress == null) {
            return null;
        }
        OrderAddressDTO o = GenericAndJson.jsonToObject(this.snapAddress,
                new TypeReference<OrderAddressDTO>() {
                });
        return o;
    }

    public void setSnapAddress(OrderAddressDTO address) {
        this.snapAddress = GenericAndJson.objectToJson(address);
    }
}
