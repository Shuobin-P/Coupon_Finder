package com.google.couponfinder.service.impl;

import com.github.pagehelper.Page;
import com.google.couponfinder.entity.Coupon;
import com.google.couponfinder.mapper.CouponMapper;
import com.google.couponfinder.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/15 20:25
 */
@Slf4j
@Service
public class CouponServiceImpl implements CouponService {
    private CouponMapper couponMapper;

    @Autowired
    public CouponServiceImpl(CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
    }

    @Override
    public Page<Coupon> getHotCoupons() {
        Page<Coupon> list = couponMapper.getHotCoupons();
        log.info("热点优惠券数据：" + list.toString());
        return list;
    }
}
