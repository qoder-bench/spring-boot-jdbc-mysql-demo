Spring Boot 4.0 Demo
=====================

Spring Boot demo with MySQL and JDBC.

# Get Started

- Java 21
- Start MySQL: `docker compopose up -d`, and listen on port 13306
- Run database migration and example dataset filling: `just dbunit-operation`
- Start App: `mvn spring-boot:run`