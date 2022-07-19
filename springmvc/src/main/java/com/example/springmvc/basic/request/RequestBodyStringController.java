package com.example.springmvc.basic.request;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestBodyStringController {
    @PostMapping("/request-body/string-v1")
    public HttpEntity<String> body(HttpEntity<String> httpEntity) {
        System.out.println(httpEntity.getBody());

        return new HttpEntity<>("ok");
    }
    @PostMapping("/request-body/string-v2")
    public ResponseEntity<String> body2(RequestEntity request) {
        String s = request.getBody().toString();
        System.out.println(s);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/request-body/string-v3")
    public ResponseEntity<String> body3(@RequestBody String body) {
        System.out.println(body);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
