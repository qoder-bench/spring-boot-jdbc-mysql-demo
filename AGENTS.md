# Spring Boot application demo

This is a demo application with Spring Boot with DDD architecture, MySQL 9,
Spring Data JDBC, Flyway for database migration, DBUnit for database test.

## Tech Stack

- Java 21
- Apache Maven 3.9.11: `flyway-maven-plugin` and `dbunit-maven-plugin` included.
- Spring Boot 3.5 with Spring MVC
- Junit 5.13
- AssertJ: Fluent assertions, please use it instead of JUnit assertions.

## Project structure

- base package: `org.mvnsearch`
- RESTful API controller package: `org.mvnsearch.web.rest`
- Spring Boot main class: `org.mvnsearch.SpringBoot4DemoApp`
- project base test: `org.mvnsearch.ProjectBaseTest`，new test class should extend `org.mvnsearch.ProjectBaseTest`

The project adopts Domain Driven Design architecture, and the domain base package is `org.mvnsearch.domain`.

- model package: `org.mvnsearch.domain.model`
- service package: `org.mvnsearch.domain.service`
- repository package: `org.mvnsearch.domain.repository`
- infrastructure package: `org.mvnsearch.domain.infra`
- implementation package for domain: `org.mvnsearch.domain.impl`, and interface and implementation mapping as below:
    - service (repository or infra) interface naming: `org.mvnsearch.domain.service.XxxxService`
    - service (repository or infra) implementation naming: `org.mvnsearch.domain.impl.service.XxxxServiceImpl`

## Database

- Main database: MySQL 9.4
- Database migration: Flyway 11 and migration script directory: `src/main/resources/db/migration`
- Database test: DbUnit with XML dataset, and dataset directory: `src/main/resources/db/dataset`
- Spring Data JDBC: ORM for database access with JPA annotations

## API Conventions

- RESTful API controller base package: `org.mvnsearch.web.rest`
- Base URL: `/api/v1`

## Build and run

The project uses Maven to build and run the project, and some tasks as following:

- Build: `mvn -DskipTests package`
- Run: `mvn spring-boot:run`

For other tasks, the project uses [just](https://github.com/casey/just) as the task runner,
and the available recipes are as below:

```
Available recipes:
    build            # build the project
    database-migrate # database migration with flyway
    dbunit-operation # DBUnit operation to fill dataset
    mysql-cli        # mysql CLI
    sbom-generate    # SBOM generation: target/application.cdx.json
```

## Java Guide line

- Null Safety: use JSpecify to annotate nullable and non-nullable types: `org.jspecify.annotations.NonNull` and
  `org.jspecify.annotations.Nullable`
- Use slf4j for logging
- Use Jakarta EE instead of Java EE:  for example `jakarta.servlet.*` instead of `javax.servlet.*`

## Configuration

The project uses `src/main/resources/application.properties` as the configuration file.

