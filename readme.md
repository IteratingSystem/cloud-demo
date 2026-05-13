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
cd nacos-server-3.2.0\nacos\bin
startup.cmd -m standalone

#### 2.5 微服务配置
在微服务的 application.yml 中添加：
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

