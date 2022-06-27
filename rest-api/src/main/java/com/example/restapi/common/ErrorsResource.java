package com.example.restapi.common;

import com.example.restapi.index.IndexController;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class ErrorsResource extends EntityModel<Errors> {
    @JsonUnwrapped
    private Errors errors;
    public ErrorsResource(Errors errors) {
        super(errors);
        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }
}
