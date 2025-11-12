# Spring Boot JDBC MySQL Demo

一个基于 [Spring Boot](https://spring.io/projects/spring-boot) 和 MySQL 的企业级应用示例项目。采用 DDD（领域驱动设计）架构，展示了如何使用 Spring Data JDBC、Flyway 数据库迁移和 DBUnit 测试来构建可维护的 Java 应用。

## 技术栈

- **Java**: 21
- **Spring Boot**: 3.5.7
- **Spring Data JDBC**: 数据访问层
- **H2 Database**: 内存数据库（默认）
- **MySQL**: 9.4.0（可选）
- **Flyway**: 11.15.0 (数据库迁移)
- **DBUnit**: 1.2.0 (数据库测试)
- **Maven**: 3.9.11+ (构建工具)
- **JUnit**: 5.13+ (单元测试)
- **AssertJ**: 流式断言库
- **jspecify**: 1.0.0 (类型注解)
- **ErrorProne + NullAway**: 静态代码分析

## 项目结构

```
spring-boot-jdbc-mysql-demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/mvnsearch/
│   │   │       ├── SpringBoot4DemoApp.java    # 应用入口
│   │   │       ├── domain/                     # 领域层
│   │   │       │   ├── model/                  # 领域模型
│   │   │       │   │   └── Account.java
│   │   │       │   ├── service/                # 领域服务接口
│   │   │       │   │   └── AccountService.java
│   │   │       │   ├── repository/             # 仓储接口
│   │   │       │   │   └── AccountRepository.java
│   │   │       │   └── impl/                   # 领域实现
│   │   │       │       └── service/
│   │   │       │           └── AccountServiceImpl.java
│   │   │       └── web/
│   │   │           └── rest/                   # RESTful API
│   │   │               ├── AccountController.java
│   │   │               └── PortalController.java
│   │   └── resources/
│   │       ├── application.properties          # 应用配置
│   │       └── db/
│   │           ├── migration/                  # Flyway 迁移脚本
│   │           │   └── V1__create_account_table.sql
│   │           └── dataset/                    # DBUnit 测试数据
│   │               └── account-dataset.xml
│   └── test/
│       └── java/
│           └── org/mvnsearch/
│               ├── ProjectBaseTest.java        # 测试基类
│               └── domain/
│                   ├── repository/
│                   │   └── AccountRepositoryTest.java
│                   └── impl/
│                       └── service/
│                           └── AccountServiceImplTest.java
├── docker-compose.yaml                         # Docker Compose 配置
├── pom.xml                                     # Maven 配置
├── Justfile                                    # Just 任务定义
└── README.md
```

## 架构设计

### DDD 领域驱动设计

项目采用 DDD 架构，清晰分离各层职责：

**领域层 (Domain Layer)** - `org.mvnsearch.domain`
- **Model**: 领域模型/实体 (`org.mvnsearch.domain.model`)
- **Service**: 领域服务接口 (`org.mvnsearch.domain.service`)
- **Repository**: 仓储接口 (`org.mvnsearch.domain.repository`)
- **Implementation**: 接口实现 (`org.mvnsearch.domain.impl`)

**应用层 (Application Layer)** - `org.mvnsearch.web`
- **REST API**: RESTful 控制器 (`org.mvnsearch.web.rest`)
- **Base URL**: `/api/v1`

### 命名约定

- 服务接口: `XxxxService`
- 服务实现: `XxxxServiceImpl` (位于 `impl.service` 包)
- 仓储接口: `XxxxRepository`
- 仓储实现: `XxxxRepositoryImpl` (位于 `impl.repository` 包)

## 功能特性

- ✅ DDD 领域驱动设计架构
- ✅ Spring Data JDBC 数据访问
- ✅ Flyway 数据库版本管理
- ✅ DBUnit 数据库集成测试
- ✅ RESTful API 设计
- ✅ Docker Compose 环境
- ✅ Maven 插件集成
- ✅ Virtual Threads 支持
- ✅ ErrorProne 静态分析
- ✅ NullAway 空指针检查

## 快速开始

### 前置要求

- Java 21 或更高版本
- Maven 3.9.11+ 或更高版本
- (可选) [just](https://github.com/casey/just) - 任务运行器
- (可选) Docker & Docker Compose (如需使用真实 MySQL)

### 数据库配置

**默认使用 H2 内存数据库**，无需额外安装：
- **JDBC URL**: jdbc:h2:mem:testdb
- **用户名**: sa
- **密码**: (空)
- **H2 控制台**: http://localhost:8080/h2-console

**如需使用 MySQL**:
1. 启动 MySQL: `docker-compose up -d`
2. 修改 `application.properties`，注释 H2 配置，取消注释 MySQL 配置
3. MySQL 配置：
   - **端口**: 3306
   - **数据库**: test
   - **用户名**: root
   - **密码**: 123456

### 数据库迁移

使用 Flyway 进行数据库迁移：

```bash
# 使用 just (推荐)
just database-migrate

# 或使用 Maven
mvn flyway:clean
mvn flyway:migrate
```

### 构建和运行

```bash
# 构建项目
mvn clean package

# 运行应用
mvn spring-boot:run

```

应用将在 `http://localhost:8080` 启动。

### 访问应用

- **健康检查**: http://localhost:8080/actuator/health
- **账户 API**: http://localhost:8080/api/v1/accounts
- **H2 控制台**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - 用户名: `sa`
  - 密码: (留空)

## API 端点

### 账户管理 API

#### 获取所有账户
```http
GET http://localhost:8080/api/v1/accounts
```

#### 根据手机号查询账户
```http
GET http://localhost:8080/api/v1/accounts/by-phone/{phone}
```

## 配置

### application.properties

项目默认配置使用 H2 内存数据库：

```properties
# H2 内存数据库配置
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DATABASE_TO_UPPER=FALSE;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=

# H2 控制台
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

```

**切换到 MySQL**：在 `application.properties` 中注释 H2 配置，取消注释 MySQL 配置即可。

### H2 控制台使用

访问 http://localhost:8080/h2-console 后：

1. **JDBC URL**: 输入 `jdbc:h2:mem:testdb`
2. **User Name**: 输入 `sa`
3. **Password**: 留空
4. 点击 "Connect" 连接

连接成功后可以：
- 查看表结构
- 执行 SQL 查询
- 管理数据

### 注意事项

**关于大小写**：
- H2 配置了 `DATABASE_TO_UPPER=FALSE` 以保持表名为小写（`account`）
- 但列名使用大写（`ID`, `USERNAME` 等），这是为了与 Spring Data JDBC 的默认行为保持一致
- Spring Data JDBC 生成的 SQL 查询默认使用大写列名
- 如果你在 H2 控制台中手写 SQL，可以使用小写或大写列名，H2 会自动匹配

## 参考资源

- [Spring Boot 文档](https://docs.spring.io/spring-boot/index.html)
- [Spring Data JDBC 文档](https://docs.spring.io/spring-data/jdbc/reference/)
- [H2 Database 文档](https://www.h2database.com/html/main.html)
- [Flyway 文档](https://flywaydb.org/documentation/)
- [DBUnit 文档](http://dbunit.sourceforge.net/)
- [MySQL 文档](https://dev.mysql.com/doc/)
- [jspecify 文档](https://jspecify.dev/)
- [AssertJ 文档](https://assertj.github.io/doc/)
- [Just 命令运行器](https://github.com/casey/just)

## 许可证

本项目遵循项目根目录下 LICENSE 文件中指定的许可证。
