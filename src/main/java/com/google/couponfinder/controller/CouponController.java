package com.google.couponfinder.controller;

import com.google.couponfinder.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/14 17:52
 */

@RestController
@RequestMapping("/coupon")
public class CouponController {
    @GetMapping("/getHotCoupons")
    public ResultVO getHotCoupons() {
        return null;
    }
}
