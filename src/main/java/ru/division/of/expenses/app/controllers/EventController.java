package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.User;
import ru.division.of.expenses.app.services.EventService;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findEventById(@PathVariable Long id) {
        return eventService.findEventById(id);
    }

    @GetMapping
    public List<EventDto> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size

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

    @PutMapping
    public ResponseEntity<?> updateEvent(@RequestBody Event event){
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id){
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

//Петя, привет!
// я бы сделал как-то типа так:
//    @GetMapping
//    public List<EventDto> getCurrentUserOrders(Principal principal) {
//        return eventService.findAllEventsByOwnerName(principal.getName()).stream().map(EventDto::new).collect(Collectors.toList());
//    }

    @GetMapping("/byManager")
    public List<EventDto> findEventsByUserId(
            Principal principal,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        if (page <= 0) {
            page = 1;
        }
        principal.getName();
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        return eventService.findEventsByUserId(
                user.getId(),
                page,
                size
        );
    }

//    @GetMapping("/byManager")
//    public List<EventDto> findEventsByUserId(
//            @AuthenticationPrincipal User user,
//            @RequestParam(name = "page", defaultValue = "1") int page,
//            @RequestParam(name = "size", defaultValue = "5") int size
//    ) {
//        if (page <= 0) {
//            page = 1;
//        }
//        return eventService.findEventsByUserId(
//                user.getId(),
//                page,
//                size
//        );
//    }

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
