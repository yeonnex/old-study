package com.example.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class LogTestController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping ("/log-test")
    public String logTest() {
        String name = "Spring";
        logger.trace("log {}" , name);
        return "ok";
    }
}
