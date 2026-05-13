package org.example.controller;


import org.example.result.R;
import org.example.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * 登录接口 - 生成 Token
     */
    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> loginInfo) {
        Long userId = Long.valueOf(loginInfo.get("userId").toString());
        String username = loginInfo.get("userName").toString();

        String token = JwtUtil.generateToken(userId, username);

        return token;
    }

    /**
     * 验证 Token 接口
     */
    @GetMapping("/verify")
    public Map<String, Object> verify(@RequestParam String token) {
        boolean isValid = JwtUtil.validateToken(token);

        Map<String, Object> result = new HashMap<>();
        if (isValid) {
            result.put("code", 200);
            result.put("message", "Token 有效");
            result.put("userId", JwtUtil.getUserId(token));
            result.put("username", JwtUtil.getUsername(token));
        } else {
            result.put("code", 401);
            result.put("message", "Token 无效或已过期");
        }
        return result;
    }

    /**
     * 获取 Token 中的用户信息
     */
    @GetMapping("/info")
    public Map<String, Object> getUserInfo(@RequestParam String token) {
        Map<String, Object> result = new HashMap<>();
        if (JwtUtil.validateToken(token)) {
            result.put("code", 200);
            result.put("userId", JwtUtil.getUserId(token));
            result.put("username", JwtUtil.getUsername(token));
            result.put("expiration", JwtUtil.getExpirationDate(token));
        } else {
            result.put("code", 401);
            result.put("message", "Token 无效");
        }
        return result;
    }
}
