package com.example.demorestapi.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) throws JsonProcessingException {
        // 먼저 입력값 자체 검증
        if (errors.hasErrors()) {
            ErrorResource errorResource = new ErrorResource(errors);
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        // 비즈니스 로직 검증
        eventValidator.validate(eventDto, errors);

        if (errors.hasErrors()) {
            ErrorResource errorResource = new ErrorResource(errors);
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        Event event = modelMapper.map(eventDto, Event.class);
        event.update();

        Event newEvent = eventRepository.save(event);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class)
                .slash(newEvent.getId());
        URI createdUri = selfLinkBuilder.toUri();
        EventResource eventResource = new EventResource(newEvent);
        eventResource.add(selfLinkBuilder.withRel("update-event"));
        eventResource.add(linkTo(EventController.class).withRel("query-events"));

        return ResponseEntity.created(createdUri).body(eventResource);
    }

    /**
     * 이벤트 페이징 조회
     * @param pageable
     * @return
     */

    @GetMapping
    public ResponseEntity<Page<Event>> queryEvents(Pageable pageable) {
        return ResponseEntity.ok(eventRepository.findAll(pageable));
    }

    /**
     * 이벤트 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 이벤트를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(event);
    }

    /**
     * 이벤트 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id,
                                      @RequestBody @Valid EventDto eventDto,
                                      Errors errors) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }

        this.eventValidator.validate(eventDto, errors);

        Event existingEvent = optionalEvent.get();
        modelMapper.map(eventDto, existingEvent);
        Event updated = eventRepository.save(existingEvent);

        return ResponseEntity.ok(updated);
    }


}
