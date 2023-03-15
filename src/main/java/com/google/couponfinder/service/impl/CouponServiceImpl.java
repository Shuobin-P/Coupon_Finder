package com.google.couponfinder.service.impl;

import com.github.pagehelper.Page;
import com.google.couponfinder.dto.CouponDetailDTO;
import com.google.couponfinder.entity.Coupon;
import com.google.couponfinder.mapper.CardPackageCouponMapper;
import com.google.couponfinder.mapper.CouponMapper;
import com.google.couponfinder.mapper.UserMapper;
import com.google.couponfinder.service.CouponService;
import com.google.couponfinder.util.TokenUtils;
import com.google.couponfinder.vo.ResultVO;
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
    private final UserMapper userMapper;
    private final CouponMapper couponMapper;
    private final CardPackageCouponMapper cardPackageCouponMapper;
    private final TokenUtils tokenUtils;

    @Autowired
    public CouponServiceImpl(UserMapper userMapper, CouponMapper couponMapper, CardPackageCouponMapper cardPackageCouponMapper, TokenUtils tokenUtils) {
        this.userMapper = userMapper;
        this.couponMapper = couponMapper;
        this.cardPackageCouponMapper = cardPackageCouponMapper;
        this.tokenUtils = tokenUtils;
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

    @Override
    public ResultVO getCoupon(String jwt, Long id) {
        //先检查数据库中的数量是否>=1，如果是的话，就-1，并把该优惠券存入该用户的卡包中，而且
        //领取一张优惠券
        Coupon coupon = couponMapper.getCouponInfo(id);
        String open_id = tokenUtils.getUsernameByToken(jwt);
        Long cardPackageID = userMapper.getCardPackageID(open_id);
        //FIXME 下面这一行有问题
        if (cardPackageCouponMapper.getRecord(cardPackageID, id) != null && coupon.getTotalQuantity() - coupon.getUsedQuantity() - coupon.getCollectedQuantity() >= 1) {
            //将该coupon的collected_quantity + 1，并在该用户对应的卡包中加入该优惠券信息
            couponMapper.plusCouponCollectedQuantity(id);
            cardPackageCouponMapper.getNewCoupon(cardPackageID, id);
            return ResultVO.getInstance("成功领取优惠券", null);
        }
        return ResultVO.getInstance("领取失败", null);
    }
}
