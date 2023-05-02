package com.google.couponfinder.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.couponfinder.entity.CardPackageCoupon;
import com.google.couponfinder.entity.Coupon;
import com.google.couponfinder.service.CouponService;
import com.google.couponfinder.service.WalletService;
import com.google.couponfinder.util.QRCodeUtil;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/14 17:52
 */
@Slf4j
@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;
    private final WalletService walletService;


    @Autowired
    public CouponController(CouponService couponService, WalletService walletService) {
        this.couponService = couponService;
        this.walletService = walletService;
    }

    /**
     * 获得使用数量最多的优惠券的相关信息，并且按照从高到低的顺序排列。
     *
     * @return
     */
    @GetMapping("/getHotCoupons")
    public ResultVO getHotCoupons(@RequestParam Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Coupon> page = couponService.getHotCoupons();
        return ResultVO.getInstance("成功获得饮品使用数量最多的优惠券相关信息", page);
    }

    @GetMapping("/getHotFoodCoupons")
    public ResultVO getHotFoodCoupons(@RequestParam Integer foodPageNum, Integer pageSize) {
        PageHelper.startPage(foodPageNum, pageSize);
        Page<Coupon> page = couponService.getHotFoodCoupons();
        return ResultVO.getInstance("成功获得食物使用数量最多的优惠券相关信息", page);
    }

    @GetMapping("/getHotOtherCoupons")
    public ResultVO getHotOtherCoupons(@RequestParam Integer otherPageNum, Integer pageSize) {
        PageHelper.startPage(otherPageNum, pageSize);
        Page<Coupon> page = couponService.getOtherHotCoupons();
        return ResultVO.getInstance("成功获得其他类型使用数量最多的优惠券相关信息", page);
    }

    @GetMapping("/getCouponInfo")
    public ResultVO getCouponInfo(@RequestParam Long id) {
        return ResultVO.getInstance("成功获得优惠券详细信息", couponService.getCouponDetail(id));
    }

    /**
     * 领取优惠券
     *
     * @param Authorization
     * @param couponId
     * @return
     */
    @GetMapping("/getCoupon")
    public ResultVO getCoupon(@RequestHeader String Authorization, @RequestParam Long couponId) {
        if (Authorization == null) {
            return ResultVO.getInstance(400, "用户未登录", null);
        }
        return couponService.getCoupon(Authorization, couponId);
    }

    /**
     * 模糊查询优惠券
     *
     * @param Authorization
     * @param queryInfo
     * @return
     */
    @GetMapping("/findCoupon")
    public ResultVO findCoupon(@RequestHeader String Authorization, @RequestParam String queryInfo, Integer pageNum, Integer pageSize) {
        if (Authorization == null) {
            return ResultVO.getInstance(400, "用户未登录", null);
        }
        PageHelper.startPage(pageNum, pageSize);
        Page<Coupon> page = couponService.findCoupon(queryInfo);
        return ResultVO.getInstance("成功查询到相关的优惠券信息", page);
    }

    @GetMapping("/generateQRCode")
    public void generateQR(@RequestParam("content") String content, HttpServletResponse response) {
        BufferedImage image;
        // 禁止图像缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        image = QRCodeUtil.createImage(content);
        // 创建二进制的输出流
        try (ServletOutputStream sos = response.getOutputStream()) {
            // 将图像输出到Servlet输出流中。
            ImageIO.write(image, "jpeg", sos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/useCoupon")
    public ResultVO useCoupon(@RequestParam Long coupon_id, Long wallet_id) {
        //验证请求是否是商家发出的，然后检查coupon_id和wallet_id是否合法
        //注意：可以使用微信开发者的多账户调试，然后开始真机调试模拟商家扫描用户出示的优惠券二维码操作。
        log.info("coupon_id为" + coupon_id);
        log.info("wallet_id为" + wallet_id);
        CardPackageCoupon cardPackageCoupon = walletService.getRecord(wallet_id, coupon_id);
        Byte status = cardPackageCoupon.getStatus();
        if (status == 1) {
            walletService.useCoupon(coupon_id, wallet_id);
            return ResultVO.getInstance(1, "成功使用优惠券", null);
        }
        return ResultVO.getInstance(0, "使用失败,该优惠券不可被使用", null);
    }
}
