package com.example.fulldev.service;

import com.example.fulldev.dto.enumeration.CouponStatus;
import com.example.fulldev.exception.NotFoundException;
import com.example.fulldev.exception.ParameterException;
import com.example.fulldev.model.Activity;
import com.example.fulldev.model.Coupon;
import com.example.fulldev.model.UserCoupon;
import com.example.fulldev.repository.ActivityRepository;
import com.example.fulldev.repository.CouponRepository;
import com.example.fulldev.repository.UserCouponRepository;
import com.example.fulldev.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    public List<Coupon> getByCategory(Long cid) {
        Date now = new Date();
        return couponRepository.findByCategory(cid, now);
    }

    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return couponRepository.findByWholeStore(true, now);
    }

    public void collectionOneCoupon(Long uid, Long couponId) {
        this.couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException(40003));
        Activity activity = this.activityRepository.findByCouponListId(couponId)
                .orElseThrow(() -> new NotFoundException(40010));
        Date now = new Date();
        Boolean isIn = CommonUtil.isInTimeLine(now, activity.getStartTime(), activity.getEndTime());
        if (!isIn) {
            throw new ParameterException(40005);
        }
        this.userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId)
                .ifPresent((uc) -> new ParameterException(40006));
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setCouponId(couponId);
        userCoupon.setUserId(uid);
        userCoupon.setStatus(CouponStatus.AVAILABLE.getValue());
        userCoupon.setCreateTime(now);

        userCouponRepository.save(userCoupon);
    }

    public List<Coupon> getMyAvailableCoupons(Long uid) {
        Date now = new Date();
        return this.couponRepository.findMyAvailable(uid, now);
    }

    public List<Coupon> getMyUsedCoupons(Long uid) {
        Date now = new Date();
        return this.couponRepository.findMyUsed(uid, now);
    }

    public List<Coupon> getMyExpiredCoupons(Long uid) {
        Date now = new Date();
        return this.couponRepository.findMyExpired(uid, now);
    }
}
