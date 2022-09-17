package com.example.demorestapi.events;

import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.Errors;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class ErrorResource extends EntityModel<Errors> {
    public ErrorResource(Errors error, Link... links) {
        super(error, Arrays.asList(links));
        add(linkTo(EventController.class).slash("").withRel("events"));
    }
}
