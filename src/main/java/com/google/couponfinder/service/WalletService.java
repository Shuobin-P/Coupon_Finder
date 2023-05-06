package com.google.couponfinder.service;

import com.github.pagehelper.Page;
import com.google.couponfinder.dto.WalletCouponDTO;
import com.google.couponfinder.dto.WalletUsedCouponDTO;
import com.google.couponfinder.entity.CardPackageCoupon;
import com.google.couponfinder.vo.ResultVO;
import org.springframework.stereotype.Service;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/14 16:28
 */
@Service
public interface WalletService {
    Page<WalletCouponDTO> getAvailableCoupons(String jwt);
    Page<WalletUsedCouponDTO> getCouponUsedHistory(String jwt);

    ResultVO deleteCoupon(String jwt, Long id);

    ResultVO getWalletID(String jwt);

    CardPackageCoupon getRecord(Long wallet_id, Long coupon_id);

    void useCoupon(Long coupon_id, Long wallet_id);
}
