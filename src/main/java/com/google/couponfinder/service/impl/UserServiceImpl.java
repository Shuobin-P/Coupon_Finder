package com.google.couponfinder.service.impl;

import com.google.couponfinder.entity.UserRole;
import com.google.couponfinder.mapper.RoleMapper;
import com.google.couponfinder.mapper.UserMapper;
import com.google.couponfinder.mapper.UserRoleMapper;
import com.google.couponfinder.service.UserService;
import com.google.couponfinder.util.HttpUtils;
import com.google.couponfinder.util.TokenUtils;
import com.google.couponfinder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/11 16:15
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Value(value = "${mini.appid}")
    private String appid;

    @Value(value = "${mini.appsecret}")
    private String secret;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    private final UserRoleMapper userRoleMapper;

    private final TokenUtils tokenUtils;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper, UserRoleMapper userRoleMapper, TokenUtils tokenUtils) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.tokenUtils = tokenUtils;
    }


    @Override
    public ResultVO login(String code) {
        log.info("Code的值为:" + code);
        //用户唯一标识
        String openid;
        //会话密钥
        String session_key;
        Map<String, Object> responsePairMap;
        CloseableHttpResponse response = null;
        List<NameValuePair> list = new ArrayList<>();
        BasicNameValuePair pair0 = new BasicNameValuePair("appid", this.appid);
        BasicNameValuePair pair1 = new BasicNameValuePair("secret", this.secret);
        BasicNameValuePair pair2 = new BasicNameValuePair("js_code", code);
        BasicNameValuePair pair3 = new BasicNameValuePair("grant_type", "authorization_code");
        list.add(pair0);
        list.add(pair1);
        list.add(pair2);
        list.add(pair3);
        try {
            response = HttpUtils.sendGet("https://api.weixin.qq.com/sns/jscode2session", list);
            responsePairMap = HttpUtils.transferHttpEntityToMap(response.getEntity());
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return ResultVO.getInstance(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务器内部错误，登录失败", null);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResultVO.getInstance(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务器内部错误，登录失败", null);
                }
            }
        }
        if (responsePairMap.get("errcode") == null) {
            session_key = (String) responsePairMap.get("session_key");
            openid = (String) responsePairMap.get("openid");
        } else {
            log.info("错误码：" + responsePairMap.get("errcode") + "错误信息：" + responsePairMap.get("errmsg"));
            return ResultVO.getInstance(HttpStatus.SC_BAD_REQUEST, "登录失败" + responsePairMap.get("errmsg"), null);
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("username", openid);
        log.info("openid为:" + openid);
        map.put("session_key", session_key);
        map.put("created", new Date());
        //如果用户是Merchant，则在map中加入isMerchant
        UserRole userRole = userRoleMapper.get(userMapper.getUserID(openid), roleMapper.getRoleID("merchant"));
        if (userRole != null) {
            map.put("isMerchant", true);
        }
        String token = tokenUtils.generateToken(map);
        Map<String, Object> tokenMap = new HashMap<>(2);
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("token", token);
        return ResultVO.getInstance(HttpStatus.SC_OK, "登录成功，获得JWT", tokenMap);
    }
}
