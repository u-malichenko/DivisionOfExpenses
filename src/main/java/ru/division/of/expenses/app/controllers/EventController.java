package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.exceptions_handling.EventNotFoundException;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.services.EventService;
import ru.division.of.expenses.app.utils.EmptyJsonResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

//    @GetMapping("/{id}")
//    public EventDto findEventById(@PathVariable("id") Long id) throws EventNotFoundException {
//        return eventService.findEventById(id);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> findEventById(@PathVariable Long id) {
//        EventDto eventDto = eventService.findEventById(id);
//        if(eventDto.getId() != null){
//            return new ResponseEntity<EventDto>(eventDto, HttpStatus.OK);
//        }else{
//            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findEventById(@PathVariable Long id) {
        return eventService.findEventById(id);
    }

    @GetMapping
    public List<EventDto> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size

    ) {
        if (page <= 0) {
            page = 1;
        }
        return eventService.findAll(page, size);
    }

    @PostMapping
    public Event saveEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }

//    @PutMapping
//    public Event updateEvent(@RequestBody Event event) throws EventNotFoundException {
//        return eventService.updateEvent(event);
//    }

    @PutMapping
    public ResponseEntity<?> updateEvent(@RequestBody Event event){
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) throws EventNotFoundException {
        eventService.deleteEvent(id);
    }

    // Поиск событий по менеджеру
    @GetMapping("/byUserId/{id}")
    public List<EventDto> findEventsByUserId(
            @PathVariable("id") Long id,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        if (page <= 0) {
            page = 1;
        }
        return eventService.findEventsByUserId(
                id,
                page,
                size
        );
    }

    // поиск событий по любому участнику.
    @GetMapping("/byParticipantId/{id}")
    public List<EventDto> findEventByParticipantId(
            @PathVariable("id") Long id,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ){
        if (page <= 0) {
            page = 1;
        }
        return eventService.findEventByParticipantId(
                id,
                page,
                size
        );
    }

}
