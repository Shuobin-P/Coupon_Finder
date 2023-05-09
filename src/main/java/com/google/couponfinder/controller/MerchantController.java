package com.google.couponfinder.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.couponfinder.dto.ExpiredCouponDTO;
import com.google.couponfinder.dto.ReleasedValidCouponDTO;
import com.google.couponfinder.dto.UpcomingCouponDTO;
import com.google.couponfinder.mapper.CouponMapper;
import com.google.couponfinder.mapper.UserMapper;
import com.google.couponfinder.service.MerchantService;
import com.google.couponfinder.util.DateUtils;
import com.google.couponfinder.util.QiniuUtils;
import com.google.couponfinder.util.TokenUtils;
import com.google.couponfinder.vo.NewCouponInfoVO;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/21 12:45
 */
@Slf4j
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    private final DateUtils dateUtils;
    private final QiniuUtils qiniuUtils;
    private final TokenUtils tokenUtils;
    private final UserMapper userMapper;
    private final CouponMapper couponMapper;
    private final MerchantService merchantService;

    @Value("${qiniu.path}")
    private String path;

    @Autowired
    public MerchantController(DateUtils dateUtils, QiniuUtils qiniuUtils, TokenUtils tokenUtils, UserMapper userMapper, CouponMapper couponMapper, MerchantService merchantService) {
        this.dateUtils = dateUtils;
        this.qiniuUtils = qiniuUtils;
        this.tokenUtils = tokenUtils;
        this.userMapper = userMapper;
        this.couponMapper = couponMapper;
        this.merchantService = merchantService;
    }


    @GetMapping("/verify")
    public ResultVO verify(@RequestParam String key, @RequestHeader String Authorization) {
        return merchantService.verify(key, Authorization);
    }

    @PostMapping("/upload")
    public ResultVO upload(@RequestParam("file") MultipartFile uploadFile) throws IOException {
        log.info("商家上传文件楼");
        String url = qiniuUtils.upload((FileInputStream) uploadFile.getInputStream(), uploadFile.getOriginalFilename());
        log.info("图片url：" + url);
        return ResultVO.getInstance("成功上传产品图片", "http://" + path + "/" + url);
    }

    @PostMapping("/commitNewCouponInfo")
    public ResultVO upload(@RequestHeader String Authorization, @RequestBody NewCouponInfoVO couponInfoVO) {
        log.info("CouponInfo的信息为：" + couponInfoVO);
        log.info("时间戳：" + couponInfoVO.getStartDate());
        log.info("当前时间：" + dateUtils.transformMicroSecondsTimestampToDate(couponInfoVO.getStartDate()));
        log.info("过期时间：" + dateUtils.transformMicroSecondsTimestampToDate(couponInfoVO.getExpireDate()));
        return merchantService.releaseNewCoupon(Authorization, couponInfoVO);
    }

    /**
     * 拿到商家发布的，还未生效的优惠券
     *
     * @param Authorization
     * @param upcomingCouponPageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getUpcomingCoupons")
    public ResultVO getUpcomingCoupons(@RequestHeader String Authorization, @RequestParam Integer upcomingCouponPageNum, Integer pageSize) {
        if (Authorization == null) {
            return ResultVO.getInstance(400, "用户未登录", null);
        }
        String openId = tokenUtils.getUsernameByToken(Authorization);
        PageHelper.startPage(upcomingCouponPageNum, pageSize);
        Long userID = userMapper.getUserID(openId);
        Page<UpcomingCouponDTO> list = merchantService.getUpcomingCoupons(userID);
        return ResultVO.getInstance("成功查询到未开始生效的优惠券信息", list);
    }

    /**
     * 拿到商家发布且正在生效中的优惠券
     *
     * @param Authorization
     * @return
     */
    @GetMapping("/getReleasedValidCoupons")
    public ResultVO getReleasedValidCoupons(@RequestHeader String Authorization, @RequestParam Integer validCouponPageNum, Integer pageSize) {
        if (Authorization == null) {
            return ResultVO.getInstance(400, "用户未登录", null);
        }
        //根据Authorization拿到商家的user_id
        String openId = tokenUtils.getUsernameByToken(Authorization);
        PageHelper.startPage(validCouponPageNum, pageSize);
        Long userID = userMapper.getUserID(openId);
        Page<ReleasedValidCouponDTO> list = merchantService.getReleasedValidCoupons(userID);
        return ResultVO.getInstance("成功查询到已发布的优惠券信息", list);
    }

    @GetMapping("/getExpiredCoupon")
    public ResultVO getExpiredCoupon(@RequestHeader String Authorization, @RequestParam Integer expiredCouponPageNum, Integer pageSize) {
        if (Authorization == null) {
            return ResultVO.getInstance(400, "用户未登录", null);
        }
        String openId = tokenUtils.getUsernameByToken(Authorization);
        PageHelper.startPage(expiredCouponPageNum, pageSize);
        Long userID = userMapper.getUserID(openId);
        Page<ExpiredCouponDTO> list = merchantService.getExpiredCoupon(userID);
        return ResultVO.getInstance("成功查询到已过期的优惠券信息", list);
    }

    @GetMapping("/deleteUpcomingCoupon")
    public ResultVO deleteUpcomingCoupon(@RequestHeader String Authorization, @RequestParam Integer couponId) {
        if (Authorization == null) {
            return ResultVO.getInstance(400, "用户未登录", null);
        }
        String openId = tokenUtils.getUsernameByToken(Authorization);
        //TODO 其实下面这个userID可以做个优化，放在redis里面
        Long userID = userMapper.getUserID(openId);
        merchantService.deleteUpcomingCoupon(userID, couponId.longValue());
        return ResultVO.getInstance("成功删除该未发布的优惠券", null);
    }

    /**
     * 下架某已发布的优惠券
     * @param Authorization
     * @param couponId
     * @return
     */
    @GetMapping("/stopValidCoupon")
    public ResultVO stopValidCoupon(@RequestHeader String Authorization, @RequestParam Integer couponId) {
        if (Authorization == null) {
            return ResultVO.getInstance(400, "用户未登录", null);
        }
        String openId = tokenUtils.getUsernameByToken(Authorization);
        Long userID = userMapper.getUserID(openId);
        couponMapper.stopValidCoupon(userID, couponId.longValue());
        return ResultVO.getInstance("成功删除该优惠券", null);
    }

}
