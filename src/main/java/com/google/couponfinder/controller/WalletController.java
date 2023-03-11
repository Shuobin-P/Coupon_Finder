package com.google.couponfinder.controller;

import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/9 21:05
 */
@Slf4j
@RestController
@RequestMapping("/wallet")
public class WalletController {

    @GetMapping("/getAvailableCoupons")
    public ResultVO getAvailableCoupons(@RequestParam String userInfo) {
        //TODO 是否要进行身份验证？这里要拿数据的话，只需要提供用户的账号信息就行了，但是你在每个接口里面写身份认证逻辑就会让代码显得很冗余
        // 因此SpringSecurity提供了身份验证的机制，
        // 如果系统没有该用户的账号信息，则不能访问接口

        return null;
    }
}
