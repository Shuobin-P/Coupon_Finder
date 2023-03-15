package com.google.couponfinder.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.couponfinder.dto.WalletCouponDTO;
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
        // 是否要进行身份验证？这里要拿数据的话，只需要提供用户的账号信息就行了，但是你在每个接口里面写身份认证逻辑就会让代码显得很冗余
        // 因此SpringSecurity提供了身份验证的机制，
        // 如果系统没有该用户的账号信息，则不能访问接口
        PageHelper.startPage(pageNum, pageSize);
        Page<WalletCouponDTO> page = walletService.getAvailableCoupons(Authorization);
        return ResultVO.getInstance("成功获得用户可用优惠券相关信息", page);
    }

    @GetMapping("/deleteCoupon")
    public ResultVO deleteCoupon(@RequestHeader String Authorization, @RequestParam Long id) {
        //TODO 编写业务逻辑代码
        //在用户对应的卡包中删除对应的记录

        return walletService.deleteCoupon(Authorization, id);
    }
}
