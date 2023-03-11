package com.google.couponfinder.controller;

import com.google.couponfinder.service.UserService;
import com.google.couponfinder.vo.LoginVO;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/10 22:55
 */
@Slf4j
@RestController
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResultVO login(@RequestBody LoginVO loginVO) {
        //TODO get OR post
        //前端调用该接口，后台向微信服务器发送appid,appsecret,code获得session_key+open_id
        //后台存储与session_key+open_id相关的登录状态token，并发送给前端，
        //用户再请求后台接口的时候，需要携带token
        log.info(loginVO.toString());
        return userService.login(loginVO.getCode());

    }

}
