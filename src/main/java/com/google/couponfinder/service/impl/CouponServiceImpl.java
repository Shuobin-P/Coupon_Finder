package com.google.couponfinder.service.impl;

import com.github.pagehelper.Page;
import com.google.couponfinder.dto.CouponDetailDTO;
import com.google.couponfinder.entity.Coupon;
import com.google.couponfinder.mapper.CouponMapper;
import com.google.couponfinder.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public CouponDetailDTO getCouponDetail(Long id) {
        Coupon coupon = couponMapper.getCouponInfo(id);
        log.info("优惠券详细信息" + coupon);
        List<String> images = couponMapper.getCouponDetailImages(id);
        CouponDetailDTO detailDTO = new CouponDetailDTO();
        BeanUtils.copyProperties(coupon, detailDTO);
        detailDTO.setImages(images);
        return detailDTO;
    }
}
