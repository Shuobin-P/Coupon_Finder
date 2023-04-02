package com.google.couponfinder.service.impl;

import com.google.couponfinder.entity.Coupon;
import com.google.couponfinder.mapper.*;
import com.google.couponfinder.service.MerchantService;
import com.google.couponfinder.util.TokenUtils;
import com.google.couponfinder.vo.NewCouponInfoVO;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/23 21:32
 */
@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {
    @Value(value = "${mini.merchantSecret}")
    private String secret;

    private final TokenUtils tokenUtils;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final CouponMapper couponMapper;
    private final CategoryMapper categoryMapper;
    private final UserRoleMapper userRoleMapper;
    private final GoodsDetailImageMapper goodsDetailImageMapper;

    @Autowired
    public MerchantServiceImpl(TokenUtils tokenUtils, UserMapper userMapper, RoleMapper roleMapper, CouponMapper couponMapper, CategoryMapper categoryMapper, UserRoleMapper userRoleMapper, GoodsDetailImageMapper goodsDetailImageMapper) {
        this.tokenUtils = tokenUtils;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.couponMapper = couponMapper;
        this.categoryMapper = categoryMapper;
        this.userRoleMapper = userRoleMapper;
        this.goodsDetailImageMapper = goodsDetailImageMapper;
    }

    @Override
    public ResultVO verify(String key, String Authorization) {
        //获得用户提交的密钥,并进行验证,如果验证通过,则返回新的jwt，微信小程序替换已经缓存的token
        //如果验证失败，不做任何处理
        log.info("Key:" + key);
        if (!key.equals(secret)) {
            return ResultVO.getInstance(0, "密钥错误，请联系彬哥", null);
        }
        String token = tokenUtils.extractToken(Authorization);
        if (tokenUtils.isExpired(token)) {
            token = tokenUtils.refreshToken(token);
        }
        token = tokenUtils.addClaim(token, "isMerchant", true);
        //一旦完成身份认证，数据库中应该就要保存用户的商家身份信息，下次登录的时候，用户就不要进行身份验证了。
        Long userID = userMapper.getUserID(tokenUtils.getUsernameByToken(Authorization));
        Long roleID = roleMapper.getRoleID("merchant");
        userRoleMapper.insert(userID, roleID);
        Map map = new HashMap(1);
        map.put("token", token);
        return ResultVO.getInstance(1, "成功添加商家权限", map);
    }

    @Override
    public ResultVO releaseNewCoupon(NewCouponInfoVO newCouponInfoVO) {
        Coupon targetCoupon = new Coupon();
        targetCoupon.setTitle(newCouponInfoVO.getTitle());
        //判断newCouponInfoVO中的时间和系统现在的时间大小，来设置status
        //newCouponInfoVO中的时间数据是数字，不是字符串
        Long couponStartDate = newCouponInfoVO.getStartDate();
        Long sysCurrentDate = System.currentTimeMillis();
        log.info("新优惠券的开始生效时间：" + couponStartDate);
        log.info("系统当前时间：：" + sysCurrentDate);
        if (sysCurrentDate > couponStartDate) {
            targetCoupon.setStatus("1");
        } else if (sysCurrentDate < couponStartDate) {
            targetCoupon.setStatus("0");
        }
        targetCoupon.setPictureUrl(newCouponInfoVO.getProductURL());
        targetCoupon.setDescription(newCouponInfoVO.getDescription());
        targetCoupon.setTotalQuantity(newCouponInfoVO.getQuantity());
        targetCoupon.setUsedQuantity(0);
        targetCoupon.setCollectedQuantity(0);
        targetCoupon.setStartDate(new Timestamp(newCouponInfoVO.getStartDate()));
        targetCoupon.setExpireDate(new Timestamp(newCouponInfoVO.getExpireDate()));
        log.info("新插入的优惠券所属类别：" + newCouponInfoVO.getCategory());
        targetCoupon.setCategoryId(categoryMapper.getCategoryID(newCouponInfoVO.getCategory()));
        targetCoupon.setOriginalPrice(newCouponInfoVO.getOriginalPrice().doubleValue());
        targetCoupon.setPresentPrice(newCouponInfoVO.getPresentPrice().doubleValue());
        //下面这个返回值有问题，不应该是1的
        Long couponID = -1L;
        if (couponMapper.addCoupon(targetCoupon) > 0) {
            couponID = targetCoupon.getId();
        }
        //商品的详细信息图片需要额外添加
        log.info("新添加的优惠券" + newCouponInfoVO.getTitle() + "的商品详情图片数量为" + newCouponInfoVO.getProductDetailURL().length);
        for (int i = 0; i < newCouponInfoVO.getProductDetailURL().length; i++) {
            log.info("向数据库中添加" + newCouponInfoVO.getTitle() + "的详细图片数据");
            log.info("向数据库中添加" + newCouponInfoVO.getTitle() + "的CouponID为：" + couponID);
            goodsDetailImageMapper.addGoodsDetailImage(couponID, newCouponInfoVO.getProductDetailURL()[i]);
        }
        return ResultVO.getInstance("成功提交新优惠券的信息", null);
    }
}
