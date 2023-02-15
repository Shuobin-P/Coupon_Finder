package com.google.couponfinder.controller;

import com.google.couponfinder.vo.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/13 16:52
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public ResultVO getTest() {
        return ResultVO.getInstance(HttpStatus.OK.value(), "成功返回数据", "数据");
    }
}
