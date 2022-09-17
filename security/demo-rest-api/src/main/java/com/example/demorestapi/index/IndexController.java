package com.example.demorestapi.index;

import com.example.demorestapi.events.EventController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class IndexController {
    @GetMapping("/api")
    public ResponseEntity index() {
        var index = new RepresentationModel();
        index.add(linkTo(EventController.class).withRel("events"));

        return ResponseEntity.ok().body(index);
    }
}
