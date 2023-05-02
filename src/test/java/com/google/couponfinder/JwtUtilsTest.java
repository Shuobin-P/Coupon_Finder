package com.google.couponfinder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/14 17:10
 */
@Slf4j
public class JwtUtilsTest {
    private String secret = "bingbingbing";

    public void test() {
        String token = "Bearer  eyJhbGciOiJIUzUxMiJ9.eyJjcmVhdGVkIjoxNjc4NzgxOTM5NzYxLCJzZXNzaW9uX2tleSI6IjZCekI2T1d1bUU5Zjg3U1JJL2pzcEE9PSIsImV4cCI6MTY4MDU4MTkzOSwidXNlcm5hbWUiOiJvdVVydDVQek5uRGg4U2lCQnJfV3lBM19teXEwIn0.v_9oJdFhj8qoHiaaEP02gUDwUBmY5ILjMKKfF2cUZqssPSfpnjqdPqwdM60Gw1OFG0eXouJSRw6Erex-HHrDFg";
        String jwt = this.extractToken(token);
        System.out.println(this.getTokenBody(jwt));
        jwt = this.addClaim(jwt, "isMerchant", true);
        System.out.println(this.getTokenBody(jwt));
        System.out.println(jwt);

    }

    public String addClaim(String token, Object k, Object v) {
        Map map = this.getTokenBody(token);
        map.put(k, v);
        token = this.generateToken(map);
        return token;
    }

    public String extractToken(String authorizationHeaderVal) {
        return authorizationHeaderVal.substring(7);
    }

    public String getUsernameByToken(String token) {
        token = extractToken(token);
        log.info("用户名：" + this.getTokenBody(token).get("username"));
        return (String) this.getTokenBody(token).get("username");
    }

    public Claims getTokenBody(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String generateToken(Map<String, Object> map) {
        return Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(System.currentTimeMillis() + 1800000 * 1000))
                .compact();
    }
}
