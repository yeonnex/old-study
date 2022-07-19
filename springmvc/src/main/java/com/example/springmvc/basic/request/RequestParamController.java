package com.example.springmvc.basic.request;

import com.example.springmvc.basic.HelloData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestParamController {
    @GetMapping("/model-attribute")
    public String modelAttribute(@ModelAttribute HelloData helloData) {
        System.out.println(helloData);
        return "ok";
    }
}
