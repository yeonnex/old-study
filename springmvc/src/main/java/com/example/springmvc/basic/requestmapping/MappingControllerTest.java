package com.example.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class MappingControllerTest {
    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(path = "/hello-basic", method = RequestMethod.GET)
    public String helloBasic() {
        log.info("hello");
        return "ok";
    }

    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {
        log.info("userId = {}", userId);
        return "ok";
    }

    @GetMapping(value = "/mapping/header")
    public String mappingHeader(@RequestHeader("Authorization") String auth){
        log.info("Auth = {}", auth);
        return "ok";
    }

    @GetMapping(value = "/header", consumes = MediaType.APPLICATION_ATOM_XML_VALUE)
    public String test(){
        return "ok";
    }
}
