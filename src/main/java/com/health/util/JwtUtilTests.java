package com.health.util;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public class JwtUtilTests {
    public static void main(String[] args) {
        // 根据用户信息生成令牌 --------------------------
        Map<String, String> payload = new HashMap<>();
        payload.put("userId", "233");
        payload.put("userName", "ps");
        String token = JwtUtil.generateToken(payload);
        System.out.println("token: " + token);
        // --------------------------------------------

        // 解析令牌并获取用户信息 ------------------------------------------------
        try {
            DecodedJWT decodedJWT = JwtUtil.decodeToken(token);
            String userId = decodedJWT.getClaim("userId").asString();
            String userName = decodedJWT.getClaim("userName").asString();
            String exp = decodedJWT.getExpiresAt().toString();
            System.out.println("userId: " + userId);      // 取出自定义属性【用户id】
            System.out.println("userName: " + userName);  // 取出自定义属性【用户名】
            System.out.println("exp: " + exp);            // 取出默认属性【过期时间】
        } catch (JWTDecodeException e) {
            System.out.println("令牌错误");
        } catch (TokenExpiredException e) {
            System.out.println("令牌过期");
        }
    }
}
