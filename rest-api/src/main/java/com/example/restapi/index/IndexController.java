package com.example.restapi.index;

import com.example.restapi.events.EventController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/api")
    public ResponseEntity<RepresentationModel> index(){
        RepresentationModel index = new RepresentationModel();
        index.add(linkTo(EventController.class).withRel("event"));
        return ResponseEntity.ok().body(index);
    }
}
