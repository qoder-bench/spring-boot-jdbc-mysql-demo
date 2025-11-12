package org.mvnsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
//@EnableJdbcRepositories
public class SpringBoot4DemoApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot4DemoApp.class, args);
    }

}
