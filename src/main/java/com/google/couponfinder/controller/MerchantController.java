package com.google.couponfinder.controller;

import com.google.couponfinder.service.MerchantService;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/21 12:45
 */
@Slf4j
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    private final MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("/verify")
    public ResultVO verify(@RequestParam String key, @RequestHeader String Authorization) {
        return merchantService.verify(key, Authorization);
    }
}
