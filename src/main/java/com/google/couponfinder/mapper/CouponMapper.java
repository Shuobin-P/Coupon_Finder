package com.google.couponfinder.mapper;

import com.github.pagehelper.Page;
import com.google.couponfinder.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/18 20:39
 */
@Mapper
public interface CouponMapper {
    /**
     * 获得使用数量最多的优惠券的相关信息，并且按照从高到低的顺序排列
     *
     * @return
     */
    Page<Coupon> getHotCoupons();
}
