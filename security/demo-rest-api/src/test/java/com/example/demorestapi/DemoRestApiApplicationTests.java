package com.example.demorestapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class DemoRestApiApplicationTests {
    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {System.out.println("dataSource = " + dataSource);
    }

}
