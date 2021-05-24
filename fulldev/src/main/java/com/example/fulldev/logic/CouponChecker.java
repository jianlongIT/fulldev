package com.example.fulldev.logic;

import com.example.fulldev.bo.SkuOrderBO;
import com.example.fulldev.core.money.IMoneyDiscount;
import com.example.fulldev.dto.enumeration.CouponType;
import com.example.fulldev.exception.ForbiddenException;
import com.example.fulldev.exception.ParameterException;
import com.example.fulldev.model.Category;
import com.example.fulldev.model.Coupon;
import com.example.fulldev.model.UserCoupon;
import com.example.fulldev.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CouponChecker {

    private Coupon coupon;
    //private UserCoupon userCoupon;
    private IMoneyDiscount iMoneyDiscount;


    public CouponChecker(Coupon coupon, IMoneyDiscount iMoneyDiscount) {
        this.coupon = coupon;
        //this.userCoupon = userCoupon;
        this.iMoneyDiscount = iMoneyDiscount;

    }

    public void isOk() {
        Date now = new Date();
        Boolean isInTimeLine = CommonUtil.isInTimeLine(now, coupon.getStartTime(), coupon.getEndTime());
        if (!isInTimeLine) {
            throw new ForbiddenException(40007);
        }
    }

    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice, BigDecimal serverTotalPrice) {
        BigDecimal serverFinalTotalPrice;
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_OFF:
                serverFinalTotalPrice = iMoneyDiscount.discount(serverTotalPrice, this.coupon.getRate());
                break;
            case FuLL_MINUS:
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                if (serverFinalTotalPrice.compareTo(new BigDecimal("0")) <= 0) {
                    throw new ForbiddenException(50008);
                }
                break;
            default:
                throw new ParameterException(40009);

        }
        int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50008);
        }
    }

    public void canBeUsed(List<SkuOrderBO> skuOrderBOS, BigDecimal serverTotalPrice) {
        BigDecimal ordercategoryPrice;
        if (this.coupon.getWholeStore()) {
            ordercategoryPrice = serverTotalPrice;
        }else {
            List<Long> cids = coupon.getCategoryList().stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            ordercategoryPrice=this.getSumByCategoryList(skuOrderBOS,cids);
        }
        this.couponCabeUsed(ordercategoryPrice);

    }

    private void couponCabeUsed(BigDecimal ordercategoryPrice){
        switch (CouponType.toType(this.coupon.getType())){
            case FULL_OFF:
            case FuLL_MINUS:
                int compare=this.coupon.getFullMoney().compareTo(ordercategoryPrice);
                if (compare>0){
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);
        }
    }
    private BigDecimal getSumByCategoryList(List<SkuOrderBO> skuOrderBOS, List<Long> cids) {
        BigDecimal sum = cids.stream()
                .map(cid -> this.getSumByCategory(skuOrderBOS, cid))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum;
    }

    private BigDecimal getSumByCategory(List<SkuOrderBO> skuOrderBOS, Long cid) {
        BigDecimal sum = skuOrderBOS.stream()
                .filter(skuOrderBO -> skuOrderBO.getCategoryid().equals(cid))
                .map(SkuOrderBO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum;
    }


}
