package com.example.fulldev.api.v1;
import com.example.fulldev.exception.NotFoundException;
import com.example.fulldev.model.Activity;
import com.example.fulldev.service.ActivityService;
import com.example.fulldev.vo.ActivityCouponVO;
import com.example.fulldev.vo.ActivityPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/name/{name}")
    public ActivityPureVO getHomeActivity(@PathVariable String name) throws Exception {
        Activity activity = activityService.getByName(name);
        if (activity == null) {
            throw new NotFoundException(40001);
        }
        ActivityPureVO vo = new ActivityPureVO(activity);
        return vo;
    }

    @RequestMapping("/name/{name}/with_coupon")
    public ActivityCouponVO getActivityWithCoupons(@PathVariable String name) {
        Activity activity = activityService.getByName(name);
        if (activity == null) {
            throw new NotFoundException(40001);
        }
        ActivityCouponVO couponVO= new ActivityCouponVO(activity);
        return  couponVO;
    }
}
