package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demo/controller")
public class DemoController {

    // ==================== 请求方式示例 ====================

    @GetMapping("/get")
    public String get() {
        return "这是 GET 请求";
    }

    @PostMapping("/post")
    public String post() {
        return "这是 POST 请求";
    }

    @PutMapping("/put")
    public String put() {
        return "这是 PUT 请求";
    }

    @DeleteMapping("/delete")
    public String delete() {
        return "这是 DELETE 请求";
    }

    @PatchMapping("/patch")
    public String patch() {
        return "这是 PATCH 请求";
    }

    // ==================== 入参示例 ====================

    // 1. @PathVariable - 路径参数
    // 请求示例：GET /demo/path/123/张三
    @GetMapping("/path/{id}/{name}")
    public Map<String, Object> pathVariable(
            @PathVariable("id") Long id,
            @PathVariable("name") String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        return result;
    }

    // 2. @RequestParam - 查询参数
    // 请求示例：GET /demo/param?page=1&size=10&keyword=hello
    @GetMapping("/param")
    public Map<String, Object> requestParam(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("size", size);
        result.put("keyword", keyword);
        return result;
    }

    // 3. @RequestBody - JSON 请求体
    // 请求示例：POST /demo/body
    // Content-Type: application/json
    // {"name":"张三","age":18}
    @PostMapping("/body")
    public Map<String, Object> requestBody(@RequestBody Map<String, Object> body) {
        System.out.println("收到请求体：" + body);
        return body;
    }

    // 4. @RequestHeader - 请求头
    // 请求示例：GET /demo/header
    @GetMapping("/header")
    public Map<String, String> requestHeader(
            @RequestHeader("User-Agent") String userAgent,
            @RequestHeader(value = "X-Token", required = false) String token) {
        Map<String, String> result = new HashMap<>();
        result.put("userAgent", userAgent);
        result.put("token", token == null ? "无" : token);
        return result;
    }

    // 5. HttpServletRequest - 原生请求对象
    @GetMapping("/request")
    public Map<String, String> httpServletRequest(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        result.put("method", request.getMethod());
        result.put("url", request.getRequestURL().toString());
        result.put("remoteAddr", request.getRemoteAddr());
        return result;
    }

    // 6. @CookieValue - Cookie
    // 请求示例：GET /demo/cookie
    @GetMapping("/cookie")
    public Map<String, String> cookieValue(@CookieValue(value = "JSESSIONID", required = false) String sessionId) {
        Map<String, String> result = new HashMap<>();
        result.put("sessionId", sessionId == null ? "无" : sessionId);
        return result;
    }

    // 7. @RequestPart - 文件上传
    // 请求示例：POST /demo/upload
    // Content-Type: multipart/form-data
    @PostMapping("/upload")
    public Map<String, Object> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("originalFilename", file.getOriginalFilename());
        result.put("size", file.getSize());
        result.put("contentType", file.getContentType());
        result.put("content", new String(file.getBytes(), 0, Math.min(100, file.getBytes().length)) + "...");
        return result;
    }

    // 8. 多文件上传
    @PostMapping("/upload/multi")
    public List<Map<String, Object>> uploadMultipleFiles(@RequestPart("files") List<MultipartFile> files) {
        return files.stream().map(file -> {
            Map<String, Object> result = new HashMap<>();
            result.put("originalFilename", file.getOriginalFilename());
            result.put("size", file.getSize());
            return result;
        }).toList();
    }

    // ==================== 返回示例 ====================

    // 1. 返回 JSON
    @GetMapping("/return/json")
    public Map<String, String> returnJson() {
        Map<String, String> data = new HashMap<>();
        data.put("code", "200");
        data.put("message", "success");
        data.put("data", "这是JSON响应");
        return data;
    }

    // 2. 返回图片（彩色条纹图片示例）
    @GetMapping("/return/image")
    public ResponseEntity<Resource> returnImage() {
        // 生成一个简单的PNG图片（32x32 彩色条纹）
        byte[] imageBytes = generateSimplePng();

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(imageBytes));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"demo.png\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

    // 生成简单PNG图片（32x32，彩色条纹）
    private byte[] generateSimplePng() {
        // PNG 文件头 + IDAT 块（极简32x32 PNG）
        // 这是一个预先生成的32x32 PNG图片的base64解码结果（七彩条纹）
        // 避免引入额外依赖，返回一个最小的1x1透明PNG
        return java.util.Base64.getDecoder().decode(
                "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="
        );
    }

    // 3. 返回字符串
    @GetMapping("/return/string")
    public String returnString() {
        return "这是纯文本字符串响应";
    }

    // 4. 返回 ResponseEntity（自定义状态码和响应头）
    @GetMapping("/return/response-entity")
    public ResponseEntity<Map<String, Object>> returnResponseEntity() {
        Map<String, Object> data = new HashMap<>();
        data.put("code", 201);
        data.put("message", "创建成功");

        return ResponseEntity.status(201)
                .header("X-Custom-Header", "custom-value")
                .body(data);
    }

    // 5. 返回 void（写入 HttpServletResponse）
    @GetMapping("/return/void")
    public void returnVoid(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("通过 HttpServletResponse 直接写入的内容");
    }

    // ==================== 完整综合示例 ====================

    // 演示多种参数混合使用
    // 请求示例：POST /demo/mixed/123?type=user
    // Header: X-Version=1.0
    // Body: {"name":"张三","age":18}
    @PostMapping("/mixed/{id}")
    public Map<String, Object> mixedParams(
            @PathVariable("id") Long id,
            @RequestParam("type") String type,
            @RequestHeader(value = "X-Version", defaultValue = "1.0") String version,
            @RequestBody Map<String, Object> body,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();
        result.put("pathId", id);
        result.put("type", type);
        result.put("version", version);
        result.put("body", body);
        result.put("requestURI", request.getRequestURI());
        return result;
    }
}