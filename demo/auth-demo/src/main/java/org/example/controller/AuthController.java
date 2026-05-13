package org.example.controller;

import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/test-token")
    public Boolean testToken(@RequestParam String token) {
        boolean valid = jwtUtil.validateToken(token);
        return valid;
    }

    /**
     * 登录接口 - 生成 Token
     */
    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> loginInfo) {
        Long userId = Long.valueOf(loginInfo.get("userId").toString());
        String username = loginInfo.get("username").toString();

        String token = jwtUtil.generateToken(userId, username);

        return token;
    }

    /**
     * 验证 Token 接口
     */
    @GetMapping("/verify")
    public Map<String, Object> verify(@RequestParam String token) {
        boolean isValid = jwtUtil.validateToken(token);

        Map<String, Object> result = new HashMap<>();
        if (isValid) {
            result.put("code", 200);
            result.put("message", "Token 有效");
            result.put("userId", jwtUtil.getUserId(token));
            result.put("username", jwtUtil.getUsername(token));
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
        if (jwtUtil.validateToken(token)) {
            result.put("code", 200);
            result.put("userId", jwtUtil.getUserId(token));
            result.put("username", jwtUtil.getUsername(token));
            result.put("expiration", jwtUtil.getExpirationDate(token));
        } else {
            result.put("code", 401);
            result.put("message", "Token 无效");
        }
        return result;
    }
}
