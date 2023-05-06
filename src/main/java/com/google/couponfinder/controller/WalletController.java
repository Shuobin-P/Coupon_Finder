package com.google.couponfinder.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.couponfinder.dto.WalletCouponDTO;
import com.google.couponfinder.dto.WalletUsedCouponDTO;
import com.google.couponfinder.entity.Coupon;
import com.google.couponfinder.service.WalletService;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/9 21:05
 */
@Slf4j
@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/getAvailableCoupons")
    public ResultVO getAvailableCoupons(@RequestHeader String Authorization, @RequestParam Integer pageNum, Integer pageSize) {
        // 如果系统没有该用户的账号信息，则不能访问接口
        PageHelper.startPage(pageNum, pageSize);
        Page<WalletCouponDTO> page = walletService.getAvailableCoupons(Authorization);
        return ResultVO.getInstance("成功获得用户可用优惠券相关信息", page);
    }

    @GetMapping("/getCouponUsedHistory")
    public ResultVO getCouponUsedHistory(@RequestHeader String Authorization, @RequestParam Integer historyPageNum, Integer pageSize) {
        PageHelper.startPage(historyPageNum, pageSize);
        Page<WalletUsedCouponDTO> page = walletService.getCouponUsedHistory(Authorization);
        return ResultVO.getInstance("成功获得优惠券的历史使用记录", page);
    }

    @GetMapping("/deleteCoupon")
    public ResultVO deleteCoupon(@RequestHeader String Authorization, @RequestParam Long id) {
        return walletService.deleteCoupon(Authorization, id);
    }

    @GetMapping("/getWalletID")
    public ResultVO getWalletID(@RequestHeader String Authorization) {
        return walletService.getWalletID(Authorization);
    }


}
