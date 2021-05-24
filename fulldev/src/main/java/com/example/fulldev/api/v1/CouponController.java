package com.example.fulldev.api.v1;

import com.example.fulldev.core.LocalUser;
import com.example.fulldev.core.interceptors.ScopeLevel;
import com.example.fulldev.dto.enumeration.CouponStatus;
import com.example.fulldev.exception.CreateSuccess;
import com.example.fulldev.exception.ParameterException;
import com.example.fulldev.model.Coupon;
import com.example.fulldev.model.User;
import com.example.fulldev.service.CouponService;
import com.example.fulldev.vo.CouponCategoryVO;
import com.example.fulldev.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("coupon")
@RestController()
public class CouponController {


    @Autowired
    private CouponService couponService;


    @RequestMapping("/by/category/{cid}")
    public List<CouponPureVO> getCouponListByCategory(@PathVariable Long cid) {
        List<Coupon> coupons = couponService.getByCategory(cid);
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        List<CouponPureVO> vos = CouponPureVO.getList(coupons);
        return vos;
    }

    @RequestMapping("/whole_store")
    public List<CouponPureVO> getWholeStoreCouponList() {
        List<Coupon> coupons = this.couponService.getWholeStoreCoupons();
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return CouponPureVO.getList(coupons);
    }

    @ScopeLevel
    @RequestMapping("collection/{id}")
    public void cllectionCoupon(@PathVariable Long id) {
        Long uid = LocalUser.getUser().getId();
        couponService.collectionOneCoupon(uid, id);
        throw new CreateSuccess(0);
    }

    @ScopeLevel
    @RequestMapping("/myself/by/status/{status}")
    public List<CouponPureVO> getMyCouponByStatus(@PathVariable int status) {
        Long uid = LocalUser.getUser().getId();
        List<Coupon> couponList;

        //触发机制 时机 过期
        switch (CouponStatus.toType(status)) {
            case AVAILABLE:
                couponList = couponService.getMyAvailableCoupons(uid);
                break;
            case USED:
                couponList = couponService.getMyUsedCoupons(uid);
                break;
            case EXPIRED:
                couponList = couponService.getMyExpiredCoupons(uid);
                break;
            default:
                throw new ParameterException(40001);
        }
        return CouponPureVO.getList(couponList);
    }

    @ScopeLevel()
    @RequestMapping("/myself/available/with_category")
    public List<CouponCategoryVO> getUserCouponWithCategory() {
        User user = LocalUser.getUser();
        List<Coupon> coupons = couponService.getMyAvailableCoupons(user.getId());
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return coupons.stream().map(coupon -> {
            CouponCategoryVO vo = new CouponCategoryVO(coupon);
            return vo;
        }).collect(Collectors.toList());
    }
}
