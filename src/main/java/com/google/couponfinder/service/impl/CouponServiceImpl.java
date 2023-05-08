package com.google.couponfinder.service.impl;

import com.github.pagehelper.Page;
import com.google.couponfinder.dto.CouponDetailDTO;
import com.google.couponfinder.entity.Coupon;
import com.google.couponfinder.mapper.CardPackageCouponMapper;
import com.google.couponfinder.mapper.CouponMapper;
import com.google.couponfinder.mapper.UserMapper;
import com.google.couponfinder.service.CouponService;
import com.google.couponfinder.util.DateUtils;
import com.google.couponfinder.util.TokenUtils;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
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
    private final TokenUtils tokenUtils;
    private final CouponMapper couponMapper;
    private final DateUtils dateUtils;
    private final CardPackageCouponMapper cardPackageCouponMapper;

    @Autowired
    public CouponServiceImpl(UserMapper userMapper, TokenUtils tokenUtils, CouponMapper couponMapper, DateUtils dateUtils, CardPackageCouponMapper cardPackageCouponMapper) {
        this.userMapper = userMapper;
        this.tokenUtils = tokenUtils;
        this.couponMapper = couponMapper;
        this.dateUtils = dateUtils;
        this.cardPackageCouponMapper = cardPackageCouponMapper;
    }

    @Override
    public Page<Coupon> getHotCoupons() {
        Page<Coupon> list = couponMapper.getHotCoupons();
        return list;
    }

    @Override
    public Page<Coupon> getHotFoodCoupons() {
        Page<Coupon> list = couponMapper.getHotFoodCoupons();
        return list;
    }

    @Override
    public Page<Coupon> getOtherHotCoupons() {
        Page<Coupon> list = couponMapper.getOtherHotCoupons();
        return list;
    }

    @Override
    public CouponDetailDTO getCouponDetail(Long id) {
        Coupon coupon = couponMapper.getCouponInfo(id);
        List<String> images = couponMapper.getCouponDetailImages(id);
        CouponDetailDTO detailDTO = new CouponDetailDTO();
        BeanUtils.copyProperties(coupon, detailDTO);
        detailDTO.setStartDate(coupon.getStartDate().getTime());
        detailDTO.setExpireDate(coupon.getExpireDate().getTime());
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
        //先查看该用户是否领取了该优惠券，如果没有，则可以成功领取
        log.info("该用户的open_id为" + open_id);
        log.info("卡包号：" + cardPackageID + "优惠券ID:" + id);
        if (cardPackageCouponMapper.getRecord(cardPackageID, id) == null && coupon.getTotalQuantity() - coupon.getUsedQuantity() - coupon.getCollectedQuantity() >= 1) {
            couponMapper.plusCouponCollectedQuantity(id);
            cardPackageCouponMapper.getNewCoupon(cardPackageID, id);
            return ResultVO.getInstance("成功领取优惠券", null);
        } else if (cardPackageCouponMapper.getRecord(cardPackageID, id) != null) {
            return ResultVO.getInstance(HttpStatus.SC_BAD_REQUEST, "同一种优惠券用户只能领取一张", null);
        }
        return ResultVO.getInstance("领取失败", null);
    }

    @Override
    public Page<Coupon> findCoupon(String queryInfo) {
        Page<Coupon> list = couponMapper.findCoupon(queryInfo);
        return list;
    }
}
