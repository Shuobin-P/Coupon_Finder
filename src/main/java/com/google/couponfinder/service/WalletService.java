package com.google.couponfinder.service;

import com.github.pagehelper.Page;
import com.google.couponfinder.dto.WalletCouponDTO;
import org.springframework.stereotype.Service;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/14 16:28
 */
@Service
public interface WalletService {
    Page<WalletCouponDTO> getAvailableCoupons(String jwt);
}
