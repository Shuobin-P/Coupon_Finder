package com.google.couponfinder.service;

import com.github.pagehelper.Page;
import com.google.couponfinder.dto.CouponDetailDTO;
import com.google.couponfinder.entity.Coupon;
import com.google.couponfinder.vo.ResultVO;
import org.springframework.stereotype.Service;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/15 20:24
 */
@Service
public interface CouponService {
    /**
     * 获得使用数量最多的优惠券的相关信息，并且按照从高到低的顺序排列
     *
     * @return
     */
    Page<Coupon> getHotCoupons();

    Page<Coupon> getHotFoodCoupons();

    Page<Coupon> getOtherHotCoupons();


    /**
     * 获得优惠券详细信息
     *
     * @return
     */
    CouponDetailDTO getCouponDetail(Long id);

    ResultVO getCoupon(String jwt, Long id);
}
