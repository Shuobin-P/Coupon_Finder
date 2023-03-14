package com.google.couponfinder.util;

import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/13 22:29
 */
@Slf4j
public class RSAUtils {

    public static RSAPublicKey convert(String publicKeyString) {
        // 将公钥字符串解码为字节数组
        log.info("publicKeyString是否为空：" + publicKeyString);
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);

        // 使用X509EncodedKeySpec类从字节数组中重构公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (Exception e) {
            log.info("keyFactory出现问题了1");
            e.printStackTrace();
        }
        RSAPublicKey publicKey = null;
        try {
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            log.info("keyFactory出现问题了2");
            e.printStackTrace();
        }

        return publicKey;
    }
}
