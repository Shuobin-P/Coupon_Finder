package com.google.couponfinder.service;

import com.github.pagehelper.Page;
import com.google.couponfinder.dto.ExpiredCouponDTO;
import com.google.couponfinder.dto.ReleasedValidCouponDTO;
import com.google.couponfinder.dto.UpcomingCouponDTO;
import com.google.couponfinder.vo.NewCouponInfoVO;
import com.google.couponfinder.vo.ResultVO;
import org.springframework.stereotype.Service;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/23 21:30
 */
@Service
public interface MerchantService {
    ResultVO verify(String key, String Authorization);

    ResultVO releaseNewCoupon(String jwt, NewCouponInfoVO newCouponInfoVO);

    /**
     * 商家发布的，但是还未到生效时间的优惠券
     *
     * @param merchantID
     * @return
     */
    Page<UpcomingCouponDTO> getUpcomingCoupons(Long merchantID);

    /**
     * 商家发布且正在生效中的优惠券
     *
     * @param merchantID 通过了商家身份验证的user_id
     * @return
     */
    Page<ReleasedValidCouponDTO> getReleasedValidCoupons(Long merchantID);

    Page<ExpiredCouponDTO> getExpiredCoupon(Long merchantID);

    void deleteUpcomingCoupon(Long userID, Long couponID);

}
