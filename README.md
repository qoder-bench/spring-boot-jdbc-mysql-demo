Spring Boot 4.0 Demo
=====================

Spring Boot demo with MySQL and JDBC.

# Get Started

- Java 21
- Start MySQL: `docker compopose up -d`, and listen on port 13306
- Run database migration and example dataset filling: `just dbunit-operation`
- Start App: `mvn spring-boot:run`

# References

- [Cursor rules for Spring Boot](https://github.com/jabrena/cursor-rules-spring-boot): a collection of Cursor rules
  designed for Spring-Boot development.
- [Cursor AI rules for Java](https://github.com/jabrena/cursor-rules-java): A collection of System prompts for Java that
  help software engineers in their daily programming work & data pipelines