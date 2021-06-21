package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.dto.EventDtoForEditPage;
import ru.division.of.expenses.app.dto.UserDtoRemove;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.service.EventService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    // поиск Событий по участнику
    @GetMapping
    public List<EventDto> findEventsByParticipant(
            Principal principal,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        if (page <= 0) {
            page = 1;
        }
        return eventService.findEventsByParticipant(
                principal.getName(),
                page,
                size
        );
    }

    // участник События
    @GetMapping("/{id}")
    public ResponseEntity<?> findEventByIdByParticipant(Principal principal,
                                           @PathVariable Long id) {
        return eventService.findEventDtoForEditPageByIdByParticipant(principal.getName(), id);
    }

    @PostMapping
    public void saveEvent(@RequestBody Event event, Principal principal) {
        eventService.saveEventReturnDto(event, principal.getName());
    }

    // менеджер События
    @PatchMapping
    public ResponseEntity<?> updateEventByManager(@RequestBody EventDtoForEditPage EventDtoForEditPage, Principal principal) {
        return eventService.updateEventByEventDtoForEditPageByManager(EventDtoForEditPage, principal.getName());
    }

    // менеджер события
    @DeleteMapping("/{id}")
    public void deleteEventByManager(@PathVariable Long id, Principal principal) {
        eventService.deleteEventByManager(id, principal.getName());
    }









    /////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping
    public void updateEventByPrincipal(@RequestBody Event event, Principal principal) {
        eventService.updateEventByPrincipal(event, principal.getName());
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


    @GetMapping("/addToUserList/{eventId}")
    public ResponseEntity<?> addUserToEventUserList(
            @PathVariable Long eventId,
            Principal principal){
        return eventService.addUserToEventUserList(principal.getName(), eventId);
    }

    @PatchMapping("/removeFromUserList/{eventId}")
    public void removeUserFromEventUserList(@RequestBody UserDtoRemove userDtoRemove,
                                            @PathVariable Long eventId){
        eventService.removeUserFromEventUserList(userDtoRemove, eventId);
    }



}
