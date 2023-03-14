package com.google.couponfinder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/14 17:10
 */
@Slf4j
public class JwtUtilsTest {
    private String secret = "bingbingbing";

    @Test
    public void test() {
        System.out.println(getUsernameByToken("Bearer  eyJhbGciOiJIUzUxMiJ9.eyJjcmVhdGVkIjoxNjc4NzgxOTM5NzYxLCJzZXNzaW9uX2tleSI6IjZCekI2T1d1bUU5Zjg3U1JJL2pzcEE9PSIsImV4cCI6MTY4MDU4MTkzOSwidXNlcm5hbWUiOiJvdVVydDVQek5uRGg4U2lCQnJfV3lBM19teXEwIn0.v_9oJdFhj8qoHiaaEP02gUDwUBmY5ILjMKKfF2cUZqssPSfpnjqdPqwdM60Gw1OFG0eXouJSRw6Erex-HHrDFg"));
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
}
