package com.example.fulldev.repository;

import com.example.fulldev.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    Optional<UserCoupon> findFirstByUserIdAndCouponId(Long uid, Long couponId);

    @Modifying
    @Query("update UserCoupon uc\n" +
            "set uc.status=2 ,uc.orderId=:oid\n" +
            "where uc.userId=:uid\n" +
            "and uc.couponId=:couponId\n" +
            "and uc.status=1\n" +
            "and uc.orderId is null")
    int writeOff(Long couponId, Long oid, Long uid);

    @Modifying
    @Query("update UserCoupon c set c.status=1 ,c.orderId=null " +
            " where c.couponId=:couponId " +
            "and c.userId=:uid" +
            " and c.status=2 and c.orderId is not null ")
    int returnBack(Long couponId, Long uid);


}
