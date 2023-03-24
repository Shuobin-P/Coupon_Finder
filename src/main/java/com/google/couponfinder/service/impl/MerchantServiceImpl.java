package com.google.couponfinder.service.impl;

import com.google.couponfinder.service.MerchantService;
import com.google.couponfinder.util.TokenUtils;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/23 21:32
 */
@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {
    @Value(value = "${mini.merchantSecret}")
    private String secret;

    private final TokenUtils tokenUtils;

    @Autowired
    public MerchantServiceImpl(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Override
    public ResultVO verify(String key, String Authorization) {
        //获得用户提交的密钥,并进行验证,如果验证通过,则返回新的jwt，微信小程序替换已经缓存的token
        //如果验证失败，不做任何处理
        log.info("Key:" + key);
        if (!key.equals(secret)) {
            return ResultVO.getInstance(0, "密钥错误，请联系彬哥", null);
        }
        String token = tokenUtils.extractToken(Authorization);
        if (tokenUtils.isExpired(token)) {
            token = tokenUtils.refreshToken(token);
        }
        token = tokenUtils.addClaim(token, "isMerchant", true);
        return ResultVO.getInstance(1, "成功添加商家权限", token);
    }
}
