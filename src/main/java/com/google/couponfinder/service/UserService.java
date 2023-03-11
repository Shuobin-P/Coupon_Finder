package com.google.couponfinder.service;

import com.google.couponfinder.vo.ResultVO;
import org.springframework.stereotype.Service;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/11 16:12
 */

@Service
public interface UserService {
    /**
     * 小程序用户登录，后台保存登录状态，返回token
     *
     * @param code 临时登录凭证
     * @return
     */
    ResultVO login(String code);
}
