package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.services.EventService;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findEventById(@PathVariable Long id) {
        return eventService.findEventById(id);
    }

//    @GetMapping
//    public List<EventDto> findAll(
//            @RequestParam(name = "page", defaultValue = "1") int page,
//            @RequestParam(name = "size", defaultValue = "10") int size
//
//    ) {
//        if (page <= 0) {
//            page = 1;
//        }
//        return eventService.findAll(page, size);
//    }

    @PostMapping
    public Event saveEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }

//    @PostMapping("/dto")
//    public EventDto saveEventDto(@RequestBody Event event) {
//        return eventService.saveEventDto(event);
//    }

    @PutMapping
    public ResponseEntity<?> updateEvent(@RequestBody Event event){
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
    }


    //  Поиск событий по менеджеру события, Principal
    @GetMapping("/byManager")
    public List<EventDto> findEventsByUsername(
            Principal principal,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        if (page <= 0) {
            page = 1;
        }
        return eventService.findEventsByManagerUsername(
                principal.getName(),
                page,
                size
        );
    }

    // Поиск событий по участнику, Principal
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @GetMapping()
    public List<EventDto> findEventsByParticipantUsername(
            Principal principal,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ){
        if (page <= 0) {
            page = 1;
        }
        return eventService.findEventsByParticipantUsername(
                principal.getName(),
                page,
                size
        );
    }
//    @GetMapping("/byParticipant")
//    public List<EventDto> findEventsByParticipantUsername(
//            Principal principal,
//            @RequestParam(name = "page", defaultValue = "1") int page,
//            @RequestParam(name = "size", defaultValue = "5") int size
//    ){
//        if (page <= 0) {
//            page = 1;
//        }
//        return eventService.findEventsByParticipantUsername(
//                principal.getName(),
//                page,
//                size
//        );
//    }

    // Поиск событий по менеджеру события, id
    @GetMapping("/byManagerId/{id}")
    public List<EventDto> findEventsByManagerrId(
            @PathVariable("id") Long id,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        if (page <= 0) {
            page = 1;
        }
        return eventService.findEventsByManagerId(
                id,
                page,
                size
        );
    }

    // поиск событий по участнику, id
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
