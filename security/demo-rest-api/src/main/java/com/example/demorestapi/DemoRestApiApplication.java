package com.example.demorestapi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@SpringBootApplication
public class DemoRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRestApiApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    static class MyRunner implements ApplicationRunner {

        private final DataSource dataSource;


        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("args = " + args);
        }
    }


}
