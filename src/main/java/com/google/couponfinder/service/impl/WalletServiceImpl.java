package com.google.couponfinder.service.impl;

import com.github.pagehelper.Page;
import com.google.couponfinder.dto.WalletCouponDTO;
import com.google.couponfinder.mapper.CouponMapper;
import com.google.couponfinder.mapper.UserMapper;
import com.google.couponfinder.service.WalletService;
import com.google.couponfinder.util.TokenUtils;
import com.google.couponfinder.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/14 16:30
 */
@Service
public class WalletServiceImpl implements WalletService {
    private final TokenUtils tokenUtils;
    private final UserMapper userMapper;
    private final CouponMapper couponMapper;

    @Autowired
    public WalletServiceImpl(TokenUtils tokenUtils, UserMapper userMapper, CouponMapper couponMapper) {
        this.tokenUtils = tokenUtils;
        this.userMapper = userMapper;
        this.couponMapper = couponMapper;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Page<WalletCouponDTO> getAvailableCoupons(String jwt) {
        String open_id = tokenUtils.getUsernameByToken(jwt);
        Page<WalletCouponDTO> list = couponMapper.getAvailableCoupons(open_id);
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
}
