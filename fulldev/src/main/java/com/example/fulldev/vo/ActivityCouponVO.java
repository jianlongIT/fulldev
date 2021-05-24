package com.example.fulldev.vo;

import com.example.fulldev.model.Activity;

import java.util.List;
import java.util.stream.Collectors;

public class ActivityCouponVO extends ActivityPureVO {

    private List<CouponPureVO> coupons;

    public ActivityCouponVO(Activity activity) {
        super(activity);
        coupons = activity.getCouponList()
                .stream().map(CouponPureVO::new)
                .collect(Collectors.toList());
    }

    public List<CouponPureVO> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponPureVO> coupons) {
        this.coupons = coupons;
    }
}
