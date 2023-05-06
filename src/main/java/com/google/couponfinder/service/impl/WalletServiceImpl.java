package com.google.couponfinder.service.impl;

import com.github.pagehelper.Page;
import com.google.couponfinder.dto.WalletCouponDTO;
import com.google.couponfinder.dto.WalletUsedCouponDTO;
import com.google.couponfinder.entity.CardPackageCoupon;
import com.google.couponfinder.mapper.CardPackageCouponMapper;
import com.google.couponfinder.mapper.CouponMapper;
import com.google.couponfinder.mapper.UserMapper;
import com.google.couponfinder.service.WalletService;
import com.google.couponfinder.util.TokenUtils;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/14 16:30
 */
@Slf4j
@Service
public class WalletServiceImpl implements WalletService {
    private final TokenUtils tokenUtils;
    private final UserMapper userMapper;
    private final CouponMapper couponMapper;
    private final CardPackageCouponMapper cardPackageCouponMapper;

    @Autowired
    public WalletServiceImpl(TokenUtils tokenUtils, UserMapper userMapper, CouponMapper couponMapper, CardPackageCouponMapper cardPackageCouponMapper) {
        this.tokenUtils = tokenUtils;
        this.userMapper = userMapper;
        this.couponMapper = couponMapper;
        this.cardPackageCouponMapper = cardPackageCouponMapper;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Page<WalletCouponDTO> getAvailableCoupons(String jwt) {
        String open_id = tokenUtils.getUsernameByToken(jwt);
        Page<WalletCouponDTO> list = couponMapper.getAvailableCoupons(open_id);
        return list;
    }

    @Override
    public Page<WalletUsedCouponDTO> getCouponUsedHistory(String jwt) {
        String open_id = tokenUtils.getUsernameByToken(jwt);
        Page<WalletUsedCouponDTO> list = couponMapper.getCouponUsedHistory(open_id);
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public ResultVO deleteCoupon(String jwt, Long id) {
        if (jwt == null) {
            return ResultVO.getInstance(400, "请先完成身份认证", null);
        }
        String open_id = tokenUtils.getUsernameByToken(jwt);
        couponMapper.deleteCoupon(open_id, id);
        couponMapper.minusCouponCollectedQuantity(id);
        return ResultVO.getInstance("成功删除优惠券", null);
    }

    @Override
    public ResultVO getWalletID(String jwt) {
        if (jwt == null) {
            return ResultVO.getInstance(400, "请先完成身份认证", null);
        }
        String open_id = tokenUtils.getUsernameByToken(jwt);
        Long walletID = userMapper.getCardPackageID(open_id);
        return ResultVO.getInstance("成功查询到用户对于的卡包ID", walletID);
    }

    @Override
    public CardPackageCoupon getRecord(Long wallet_id, Long coupon_id) {
        CardPackageCoupon entity = cardPackageCouponMapper.getEntity(coupon_id, wallet_id);
        return entity;
    }

    @Override
    public void useCoupon(Long coupon_id, Long wallet_id) {
        cardPackageCouponMapper.updateRecordStatus((byte) 2, coupon_id, wallet_id);
    }
}
