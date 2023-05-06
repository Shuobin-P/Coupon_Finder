package com.google.couponfinder.service;

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

}
