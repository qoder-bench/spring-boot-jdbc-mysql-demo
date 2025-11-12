# Spring Boot JDBC MySQL Demo

An enterprise-level application demo project based on [Spring Boot](https://spring.io/projects/spring-boot) and MySQL. Adopts DDD (Domain-Driven Design) architecture and demonstrates how to build maintainable Java applications using Spring Data JDBC, Flyway database migrations, and DBUnit testing.

## Tech Stack

- **Java**: 21
- **Spring Boot**: 3.5.7
- **Spring Data JDBC**: Data access layer
- **H2 Database**: In-memory database (default)
- **MySQL**: 9.4.0 (optional)
- **Flyway**: 11.15.0 (Database migration)
- **DBUnit**: 1.2.0 (Database testing)
- **Maven**: 3.9.11+ (Build tool)
- **JUnit**: 5.13+ (Unit testing)
- **AssertJ**: Fluent assertions library
- **jspecify**: 1.0.0 (Type annotations)
- **ErrorProne + NullAway**: Static code analysis

## Project Structure

```
spring-boot-jdbc-mysql-demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/mvnsearch/
│   │   │       ├── SpringBoot4DemoApp.java    # Application entry point
│   │   │       ├── domain/                     # Domain layer
│   │   │       │   ├── model/                  # Domain models
│   │   │       │   │   └── Account.java
│   │   │       │   ├── service/                # Domain service interfaces
│   │   │       │   │   └── AccountService.java
│   │   │       │   ├── repository/             # Repository interfaces
│   │   │       │   │   └── AccountRepository.java
│   │   │       │   └── impl/                   # Domain implementations
│   │   │       │       └── service/
│   │   │       │           └── AccountServiceImpl.java
│   │   │       └── web/
│   │   │           └── rest/                   # RESTful API
│   │   │               ├── AccountController.java
│   │   │               └── PortalController.java
│   │   └── resources/
│   │       ├── application.properties          # Application configuration
│   │       └── db/
│   │           ├── migration/                  # Flyway migration scripts
│   │           │   └── V1__create_account_table.sql
│   │           └── dataset/                    # DBUnit test data
│   │               └── account-dataset.xml
│   └── test/
│       └── java/
│           └── org/mvnsearch/
│               ├── ProjectBaseTest.java        # Test base class
│               └── domain/
│                   ├── repository/
│                   │   └── AccountRepositoryTest.java
│                   └── impl/
│                       └── service/
│                           └── AccountServiceImplTest.java
├── docker-compose.yaml                         # Docker Compose configuration
├── pom.xml                                     # Maven configuration
├── Justfile                                    # Just task definitions
└── README.md
```

## Architecture Design

### DDD - Domain-Driven Design

The project adopts DDD architecture with clear separation of concerns:

**Domain Layer** - `org.mvnsearch.domain`
- **Model**: Domain models/entities (`org.mvnsearch.domain.model`)
- **Service**: Domain service interfaces (`org.mvnsearch.domain.service`)
- **Repository**: Repository interfaces (`org.mvnsearch.domain.repository`)
- **Implementation**: Interface implementations (`org.mvnsearch.domain.impl`)

**Application Layer** - `org.mvnsearch.web`
- **REST API**: RESTful controllers (`org.mvnsearch.web.rest`)
- **Base URL**: `/api/v1`

### Naming Conventions

- Service interface: `XxxxService`
- Service implementation: `XxxxServiceImpl` (in `impl.service` package)
- Repository interface: `XxxxRepository`
- Repository implementation: `XxxxRepositoryImpl` (in `impl.repository` package)

## Features

- ✅ DDD Domain-Driven Design architecture
- ✅ Spring Data JDBC data access
- ✅ Flyway database version control
- ✅ DBUnit database integration testing
- ✅ RESTful API design
- ✅ Docker Compose environment
- ✅ Maven plugin integration
- ✅ Virtual Threads support
- ✅ ErrorProne static analysis
- ✅ NullAway null pointer checking

## Quick Start

### Prerequisites

- Java 21 or higher
- Maven 3.9.11+ or higher
- (Optional) [just](https://github.com/casey/just) - Task runner
- (Optional) Docker & Docker Compose (if using real MySQL)

### Database Configuration

**Uses H2 in-memory database by default**, no additional installation required:
- **JDBC URL**: jdbc:h2:mem:testdb
- **Username**: sa
- **Password**: (empty)
- **H2 Console**: http://localhost:8080/h2-console

**To use MySQL**:
1. Start MySQL: `docker-compose up -d`
2. Modify `application.properties`, comment H2 config, uncomment MySQL config
3. MySQL configuration:
   - **Port**: 3306
   - **Database**: test
   - **Username**: root
   - **Password**: 123456

### Database Migration

Use Flyway for database migration:

```bash
# Using just (recommended)
just database-migrate

# Or using Maven
mvn flyway:clean
mvn flyway:migrate
```

### Build and Run

```bash
# Build project
mvn clean package

# Run application
mvn spring-boot:run
```

The application will start at `http://localhost:8080`.

### Access Application

- **Health Check**: http://localhost:8080/actuator/health
- **Account API**: http://localhost:8080/api/v1/accounts
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (leave empty)

## API Endpoints

### Account Management API

#### Get All Accounts
```http
GET http://localhost:8080/api/v1/accounts
```

#### Query Account by Phone
```http
GET http://localhost:8080/api/v1/accounts/by-phone/{phone}
```

## Configuration

### application.properties

Project default configuration uses H2 in-memory database:

```properties
# H2 in-memory database configuration
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DATABASE_TO_UPPER=FALSE;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

**Switch to MySQL**: Comment H2 configuration and uncomment MySQL configuration in `application.properties`.

### H2 Console Usage

After visiting http://localhost:8080/h2-console:

1. **JDBC URL**: Enter `jdbc:h2:mem:testdb`
2. **User Name**: Enter `sa`
3. **Password**: Leave empty
4. Click "Connect" to connect

After successful connection, you can:
- View table structure
- Execute SQL queries
- Manage data

### Notes

**About Case Sensitivity**:
- H2 is configured with `DATABASE_TO_UPPER=FALSE` to keep table names lowercase (`account`)
- Column names use uppercase (`ID`, `USERNAME`, etc.) to align with Spring Data JDBC default behavior
- Spring Data JDBC generated SQL queries use uppercase column names by default
- If you write SQL manually in H2 console, you can use lowercase or uppercase column names, H2 will match automatically

## References

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/index.html)
- [Spring Data JDBC Documentation](https://docs.spring.io/spring-data/jdbc/reference/)
- [H2 Database Documentation](https://www.h2database.com/html/main.html)
- [Flyway Documentation](https://flywaydb.org/documentation/)
- [DBUnit Documentation](http://dbunit.sourceforge.net/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [jspecify Documentation](https://jspecify.dev/)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [Just Command Runner](https://github.com/casey/just)

## License

This project follows the license specified in the LICENSE file in the project root directory.
