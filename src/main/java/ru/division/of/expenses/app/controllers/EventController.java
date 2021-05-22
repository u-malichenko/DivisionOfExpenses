package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.dto.UserDto;
import ru.division.of.expenses.app.exceptions_handling.EventNotFoundExcpetion;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.User;
import ru.division.of.expenses.app.services.EventService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    @GetMapping("/{id}")
    public EventDto findEventById(@PathVariable("id") Long id) throws EventNotFoundExcpetion {
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

    @PutMapping
    public Event updateEvent(@RequestBody Event event) throws EventNotFoundExcpetion {
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) throws EventNotFoundExcpetion {
        eventService.deleteEvent(id);
    }

    @GetMapping("/byUserId/{id}")
    public List<EventDto> findEventsByUserId(
            @PathVariable("id") Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
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

}
