package com.example.restapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EventResource extends RepresentationModel<Event> {

    @JsonUnwrapped
    private final Event event;

    public EventResource(Event event) {
        this.event = event;
        //add(Link.of("http://localhost:8080/api/events/" + event.getId(), LinkRelation.of("self")));
        add(linkTo(EventController.class).slash(event.getId()).withRel("self"));
    }
    public Event getEvent() {
        return this.event;
    }
}
