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


    @PostMapping
    public Event saveEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }


    @PutMapping
    public ResponseEntity<?> updateEventByPrincipal(@RequestBody Event event, Principal principal){
        return eventService.updateEventByPrincipal(event, principal.getName());
    }


    @DeleteMapping("/{id}")
    public void deleteEventByPrincipal(@PathVariable Long id, Principal principal){
        eventService.deleteEventByPrincipal(id, principal.getName());
    }


    //  Поиск событий по менеджеру события, Principal
    @GetMapping("/byManager")
    public List<EventDto> findEventsByManagerUsername(
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
    @GetMapping
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


    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////

    //    @PostMapping("/dto")
//    public EventDto saveEventDto(@RequestBody Event event) {
//        return eventService.saveEventDto(event);
//    }



}
