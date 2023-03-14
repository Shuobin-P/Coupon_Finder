package com.google.couponfinder.service.impl;

import com.github.pagehelper.Page;
import com.google.couponfinder.dto.WalletCouponDTO;
import com.google.couponfinder.mapper.CouponMapper;
import com.google.couponfinder.service.WalletService;
import com.google.couponfinder.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/14 16:30
 */
@Service
public class WalletServiceImpl implements WalletService {
    private final TokenUtils tokenUtils;
    private final CouponMapper couponMapper;

    @Autowired
    public WalletServiceImpl(TokenUtils tokenUtils, CouponMapper couponMapper) {
        this.tokenUtils = tokenUtils;
        this.couponMapper = couponMapper;
    }

    @Override
    public Page<WalletCouponDTO> getAvailableCoupons(String jwt) {
        String open_id = tokenUtils.getUsernameByToken(jwt);
        Page<WalletCouponDTO> list = couponMapper.getAvailableCoupons(open_id);
        return list;
    }
}
