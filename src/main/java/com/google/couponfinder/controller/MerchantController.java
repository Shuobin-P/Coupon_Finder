package com.google.couponfinder.controller;

import com.google.couponfinder.service.MerchantService;
import com.google.couponfinder.util.DateUtils;
import com.google.couponfinder.util.QiniuUtils;
import com.google.couponfinder.vo.NewCouponInfoVO;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final MerchantService merchantService;

    @Autowired
    public MerchantController(DateUtils dateUtils, QiniuUtils qiniuUtils, MerchantService merchantService) {
        this.dateUtils = dateUtils;
        this.qiniuUtils = qiniuUtils;
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
        return ResultVO.getInstance("成功上传产品图片", url);
    }

    @PostMapping("/commitNewCouponInfo")
    public ResultVO upload(@RequestBody NewCouponInfoVO couponInfoVO) {
        log.info("CouponInfo的信息为：" + couponInfoVO);
        log.info("时间戳：" + couponInfoVO.getStartDate());
        log.info("当前时间：" + dateUtils.transformMicroSecondsTimestampToDate(couponInfoVO.getStartDate()));
        log.info("过期时间：" + dateUtils.transformMicroSecondsTimestampToDate(couponInfoVO.getExpireDate()));
        return merchantService.releaseNewCoupon(couponInfoVO);
    }

    @GetMapping("/useCoupon")
    public ResultVO useCoupon(@RequestParam Long coupon_id, Long wallet_id) {
        //验证请求是否是商家发出的，然后检查coupon_id和wallet_id是否合法
        //FIXME 如何前端那边一定要使用https协议，这个功能应该如何处理？
        return null;
    }
}
