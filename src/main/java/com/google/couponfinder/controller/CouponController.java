package com.google.couponfinder.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.couponfinder.entity.Coupon;
import com.google.couponfinder.service.CouponService;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/14 17:52
 */
@Slf4j
@RestController
@RequestMapping("/coupon")
public class CouponController {

    private CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * 获得使用数量最多的优惠券的相关信息，并且按照从高到低的顺序排列。
     *
     * @return
     */
    @GetMapping("/getHotCoupons")
    public ResultVO getHotCoupons(@RequestParam Integer pageNum, Integer pageSize) {
        //可以参考个人运动管理平台SportNewsController写法
        //FIXME 要实现分页查询，就是先把所有数据查出来放在Page中，讲实话，把所有数据拿出来存储在Page里面，如果数据量很大，会出现问题吧。
        PageHelper.startPage(pageNum, pageSize);
        Page<Coupon> page = couponService.getHotCoupons();
        return ResultVO.getInstance("成功获得使用数量最多的优惠券相关信息", page);
    }


    @GetMapping("/getCouponInfo/{id}")
    public ResultVO getCouponInfo(@PathVariable Long id) {
        return ResultVO.getInstance("成功获得优惠券详细信息", couponService.getCouponDetail(id));
    }


}
