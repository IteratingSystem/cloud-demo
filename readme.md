## 1. 系统版本
- JDK 17

## 2. Nacos

### 使用版本
- Nacos 3.2.0

### 使用方式

#### 2.1 下载 Nacos
- 下载地址：https://github.com/alibaba/nacos/releases
- 选择版本：nacos-server-3.2.0.zip

#### 2.2 配置数据库
1. 创建数据库（MySQL 5.6+ 或 8.0+）：
   CREATE DATABASE nacos CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

2. 执行建表脚本：
   nacos-server-3.2.0\nacos\conf\mysql-schema.sql

#### 2.3 修改 Nacos 配置文件
编辑 nacos-server-3.2.0\nacos\conf\application.properties：

### 数据库配置
```properties
spring.sql.init.platform=mysql
db.num=1
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false&serverTimezone=Asia/Shanghai
db.user.0=root
db.password.0=你的数据库密码
```

### 关闭认证（开发环境可选）
nacos.core.auth.enabled=false

### 填写默认账号与密码

#### 2.4 启动 Nacos
- cd nacos-server-3.2.0\nacos\bin
- startup.cmd -m standalone
- 由于git上传文件的限制，我已将nacos中的
#### 2.5 微服务配置
在微服务的 application.yml 中添加：target文件（核心文件）分片压缩，需要自行解压
```yaml
spring:
  cloud:
    nacos:
      server-addr: 127.0.0.1:8848
```

#### 2.6 启动微服务
示例：service.nacos-test 服务

#### 2.7 访问 Nacos 控制台
- 地址：http://localhost:8080/
- 默认账号/密码：nacos/nacos

### Nacos 版本与 JDK 对应关系
- Nacos 3.x 需要 JDK 17
- Nacos 2.x 需要 JDK 8/11
- 详细版本对应表请参考：https://nacos.io/docs/latest/upgrading/3.0.0-compatibility/

## 3. OpenFeign 服务转发
1. 服务的Application添加注解:@EnableFeignClients,示例:openfeign-demo
2. 为转发目标创建client,示例:openfeign-demo中的DemoClient
3. 调用Client即为调用目标请求,示例:openfeign-demo中的DemoController

## 4. Sentinel 熔断与兜底

### 使用版本
- Sentinel Dashboard 1.8.9
- spring-cloud-starter-alibaba-sentinel 2023.0.3.2

### 使用方式

#### 4.1 下载并启动 Sentinel Dashboard
```bash
# 下载 sentinel-dashboard-1.8.9.jar
# 启动（指定端口，避免与 Nacos 冲突）
java -jar sentinel-dashboard-1.8.9.jar --server.port=8090
```
访问：http://localhost:8090
默认账号/密码：sentinel/sentinel

#### 4.2 微服务引入 Sentinel 依赖
```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

#### 4.3 配置 Sentinel
```yaml
spring:
  cloud:
    sentinel:
      transport:
        dashboard: localhost:8090
      eager: true

feign:
  sentinel:
    enabled: true
```
#### 4.4 三种请求的兜底方式
##### 方式一：OpenFeign + fallback（服务间调用）
##### Feign 客户端：
```java
@FeignClient(value = "controller-demo", fallback = DemoClientFallback.class)
public interface DemoClient {
    @GetMapping("/demo/controller/get")
    String get();
}
```
##### Fallback 兜底类：
```java
@Component
public class DemoClientFallback implements DemoClient {
    @Override
    public String get() {
        return "【Fallback】服务熔断，返回兜底数据";
    }
}
```

##### 方式二：@SentinelResource 注解（本地方法兜底）
```java
@Service
public class DemoService {
    @SentinelResource(value = "testMethod", fallback = "testMethodFallback")
    public String testMethod(String id) {
        // 业务逻辑
        return "success";
    }
    
    public String testMethodFallback(String id, Throwable ex) {
        return "【Fallback】方法执行失败：" + ex.getMessage();
    }
}
```

##### 方式三：RestTemplate + Sentinel（外部调用兜底）
##### 配置类：
```java
@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    @SentinelRestTemplate(
        fallback = "handleFallback",
        fallbackClass = RestTemplateFallback.class,
        blockHandler = "handleBlock",
        blockHandlerClass = RestTemplateFallback.class
    )
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```
##### Fallback 兜底类：
```java
@Component
public class RestTemplateFallback {
    public static ClientHttpResponse handleFallback(HttpRequest request, byte[] body,
                                                    ClientHttpRequestExecution execution, Throwable ex) {
        String msg = "【Fallback】调用失败：" + ex.getMessage();
        return ClientHttpResponse.create(HttpStatusCode.valueOf(200), new HttpHeaders(), msg.getBytes());
    }
    
    public static ClientHttpResponse handleBlock(HttpRequest request, byte[] body,
                                                 ClientHttpRequestExecution execution, BlockException ex) {
        String msg = "【BlockHandler】服务被限流或熔断：" + ex.getClass().getSimpleName();
        return ClientHttpResponse.create(HttpStatusCode.valueOf(200), new HttpHeaders(), msg.getBytes());
    }
}
```
## 5. Geteway 网关
- 使用后,所有的微服务,使用同意端口访问
- 原URL会变成 -> http://ip:gateway.prot/service.name(nacos中的服务名)/controller中的路径