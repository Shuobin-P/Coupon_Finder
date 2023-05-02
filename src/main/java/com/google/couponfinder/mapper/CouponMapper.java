package com.google.couponfinder.mapper;

import com.github.pagehelper.Page;
import com.google.couponfinder.dto.WalletCouponDTO;
import com.google.couponfinder.entity.Coupon;
import com.google.couponfinder.vo.NewCouponInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/18 20:39
 */
@Mapper
public interface CouponMapper {
    /**
     * 返回该Coupon的id
     *
     * @param coupon
     * @return
     */
    Long addCoupon(Coupon coupon);

    /**
     * 获得使用数量最多的优惠券的相关信息，并且按照从高到低的顺序排列
     *
     * @return
     */
    Page<Coupon> getHotCoupons();

    Page<Coupon> getHotFoodCoupons();

    Page<Coupon> getOtherHotCoupons();

    Coupon getCouponInfo(Long id);

    List<String> getCouponDetailImages(Long id);

    Page<WalletCouponDTO> getAvailableCoupons(String open_id);

    void deleteCoupon(String open_id, Long id);

    void minusCouponCollectedQuantity(Long id);

    void plusCouponCollectedQuantity(Long id);

    Page<Coupon> findCoupon(String queryInfo);

}
