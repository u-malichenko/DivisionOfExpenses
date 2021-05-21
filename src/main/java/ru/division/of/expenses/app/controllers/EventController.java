package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.services.EventService;
import ru.division.of.expenses.app.utils.MappingEventUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
@Slf4j
public class EventController {

    private final EventService eventService;

    @GetMapping("/{id}")
    public EventDto findEventDtoById(@PathVariable Long id){
        return eventService.findEventDtoById(id);
    }

    @GetMapping
    public Page<EventDto> findAllEventDto(@RequestParam(required = false, defaultValue = "0") int page , @RequestParam(required = false, defaultValue = "10") int size){
        return eventService.findAllEventDto(page, size);
    }

    //    @GetMapping("/{id}")
//    public EventDto findById(@PathVariable Long id) throws IOException {
//        try{
//
//            return new EventDto(eventService.findById(id).get());
//        }catch (Exception ioException){
//            log.error("TEST");
//        }
//        return null;
//    }

//    @GetMapping
//    public Page<EventDto> findAll(@RequestParam(required = false, defaultValue = "0") int page , @RequestParam(required = false, defaultValue = "10") int size){
//        return eventService.findAll(page, size).map(EventDto::new);
//    }

}
