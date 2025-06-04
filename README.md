# SpringCloud学习项目

### 项目简介
学习SpringCloud分布式程序开发和部署所创建的学习项目

### 分布式结构简介
```mermaid
graph TB
    User(用户) --> Browser[浏览器网址]
    Browser --> Gateway[网关,注册中心,请求路由]
    Gateway --> Service1[服务器1]
    Gateway --> Service2[服务器2]
    Gateway --> Service3[服务器3]
    Gateway --> Service...[服务器...]
    Service1 --> Server1((微服务1))
    Service1 --> Server2((微服务2))
    Service2 --> Server3((微服务3))
    Service2 --> Server4((微服务4(假设卡住)))
    Service3 --> Server5((微服务5))
    Server2 --> Remoting(远程调用)
    Remoting -.远程调用(走注册中心服务发现).-> Gateway
    Gateway --> Server5
    Server1 --> server3
    Server3 -.卡住(服务雪崩,需要引入服务熔断机制).-> Server4
```

### Maven设置
设置国内库:在`settings.xml`中的`<settings>`标签内部添加如下代码:
```xml
<!--国内库-->
<mirrors>
  <mirror>
      <id>alimaven</id>
      <mirrorOf>central</mirrorOf>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
  </mirror>
  <mirror>
      <id>uk</id>
      <mirrorOf>central</mirrorOf>
      <name>UK Maven</name>
      <url>http://uk.maven.org/maven2/</url>
  </mirror>
  <mirror>
      <id>CN</id>
      <mirrorOf>central</mirrorOf>
      <name>OSChina Central</name>
      <url>http://maven.oschina.net/content/groups/public/</url>
  </mirror>
  <mirror>
      <id>nexus</id>
      <mirrorOf>central</mirrorOf>
      <name>Nexus Repository</name>
      <url>http://repo.maven.apache.org/maven2</url>
  </mirror>
</mirrors>
```


### 框架版本
| 框架                 | 版本         |
|--------------------|------------|
| SpringCloud        | 2023.0.3   |
| SpringCloudAlibaba | 2023.0.3.2 |
| SpringBoot         | 3.3.4      |


| 组件软件     | 版本    | 功能                  | 启动方式 | 官网地址 |
|----------|-------|---------------------| --- | --- |
| Nacos    | 2.4.3 | 动态服务注册与发现,配置管理和服务管理 | startup.cmd -m standalone | https://nacos.io/ |
| Sentinel | 1.8.8 | ---                 |
| Seata    | 2.2.0 | ---                 |

### 工程结构
* cloud-demo //控制框架版本
   * services  //所有的微服务
      * service-order
      * service-product
   * 

### 创建项目
1. 新增一个工程
2. 选择SpringBoot项目创建模板
3. 确定好jdk版本(此处为17),下一页不需要修改`SpringBoot`版本(后由pom.xml统一管理)直接点击创建即可
4. 创建项目后吧多余的文件删除,只保留`pom.xml`和`.idea`(idea自带的文件夹),包括`.mvn`也需要删除
5. 修改`pom.xml`以管理版本,此为项目最外层`pom.xml`,用于控制三大框架版本,包括`SpringBoot``SpringCloud``SpringCloudAlibaba`的版本
   1. 由于此为最外层,所以添加打包方式为`pom`,代码:`<packaging>pom</packaging>`
   2. 修改`SpringBoot`的版本,此项目用的是`3.3.4`
   3. 将`dependencies`和`properties`整个删除,手动添加大件版本及依赖,此项目添加如下:
   ```xml
   <!--大件版本-->
   <properties>
     <maven.compiler.source>17</maven.compiler.source>
     <maven.compiler.target>17</maven.compiler.target>
     <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
     <spring-cloud.version>2023.0.3</spring-cloud.version>
     <spring-cloud-alibaba.version>2023.0.3.2</spring-cloud-alibaba.version>
   </properties>
   <!--使用导入的方式添加cloud和alibaba-->
   <dependencyManagement>
     <dependencies>
         <dependency>
             <groupId>org.springframwork.cloud</groupId>
             <artifactId>spring-cloud-dependencies</artifactId>
             <version>${spring-cloud.version}</version>
             <type>pom</type>
             <scope>import</scope>
         </dependency>
         <dependency>
             <groupId>com.alibaba.cloud</groupId>
             <artifactId>spring-cloud-alibaba-dependencies</artifactId>
             <version>${spring-cloud-alibaba.version}</version>
             <type>pom</type>
             <scope>import</scope>
         </dependency>
     </dependencies>
   </dependencyManagement>
   ```
   到此,三大框架的版本已设置
6. 创建services项目(模组)
   1. 选中最外层父项目,`new`一个`Module`,选择`Java`(不要选SpringBoot了),名字写`services`,点击创建
   2. 删除多余的文件只保留`pom.xml`
   3. 检查`pom.xml`,确定其父项目名称是否正确,查看
   ```xml
   <parent>
      <groupId>com.engine</groupId>
      <artifactId>cloud-demo</artifactId>
      <version>0.0.1-SNAPSHOT</version>
   </parent>
   ```
   4. 修改其`pom.xml`,由于其也是用于管理子项目的,所以加上`<packaging>pom</packaging>`设置它的打包方式
   5. 在其内部创建子项目(微服务本体),和创建`services`过程一样,最后检查其`pom.xml`中父项目是否正确,不需要设置打包方式
   6. 在`services`层的`pom.xml`中引入所有微服务都需要用到的公共依赖

### 单元测试
1. 在微服务的pom.xml中添加依赖:
```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-test</artifactId>
   <scope>test</scope>
</dependency>
```
2. 在src/test(/java)中添加注册类即可,包名需要与微服务启动类包名一致,注册类需要添加`@SpringBootTest`注解


### 注册中心Nacos
#### 服务注册
* 去官网下载Nacos

| 流程 | 内容 | 核心                                              |
|  --- | --- |-------------------------------------------------|
| 步骤1 | 启动微服务 | SpringBoot微服务web项目启动                            |
| 步骤2 | 引入服务发现依赖 | spring-cloud-starter-alibaba-nacos-discovery    |
| 步骤3 | 配置Nacos地址 | spring.cloud.nacos.server-addr = 127.0.0.1:8848 |
| 步骤4 | 查看注册中心效果 | http://127.0.0.1:8848/nacos                     |
| 步骤5 | 集群模式测试启动 | 单机情况下通过改变端口模拟微服务集群 |

* 具体实现如下:
1. `services`层的`pom.xml`添加`nacos`依赖为公共依赖
```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
2. 微服务的`pom.xml`添加依赖:
```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
3. 为微服务添加一个启动类,例如:
```java
@SpringBootApplication
public class OrderMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainApplication.class, args);
    }
}
```
4. 为微服务创建配置文件`application.properties`,例如:
```properties
#常规属性
spring.application.name=server-order
server.port=8000

#Nacos连接地址
spring.cloud.nacos.server-addr=127.0.0.1:8848
```
5. 直接启动微服务即可

#### 服务发现
| 流程 | 内容        | 核心                   |
|  --- |-----------|----------------------|
| 步骤1 | 启动微服务     | SpringBoot微服务web项目启动 |
| 步骤2 | 测试服务发现API | DiscoveryClient      |
| 步骤3 | 测试服务发现API | NacosServiceDiscovery |
