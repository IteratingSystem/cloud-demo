## 1.系统版本
### jdk 17

## 2.nacos
### 使用版本 3.2.0
### 使用方式:
    1.下载nacos:https://github.com/alibaba/nacos/releases
    2.为微服务添加nacos的配置属性:spring.cloud.nacos.server-addr:127.0.0.1:8848(示例地址)
    3.需要为nacos指定(或创建)数据库,后运行nacos-server-3.2.0\nacos\conf\mysql-schema.sql创建必须表
    4.修改nacos的配置文件,添加默认账号密码以及数据库相关属性:nacos-server-3.2.0\nacos\conf\application.properties
    5.运行nacos(需要配置jdk环境变量),使用单机模式运行:startup.cmd -m standalone
    6.启动微服务,示例:service.nacos-test服务
    7.可在网页登录nacos:http://localhost:8080/
### nacos版本与jdk对应表格可在nacos官网查看