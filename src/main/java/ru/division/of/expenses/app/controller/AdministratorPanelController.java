package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.service.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdministratorPanelController {

    private final DirectPayersService directPayersService;
    private final EventService eventService;
    private final ExpenseService expenseService;
    private final PartitialPayersService partitialPayersService;
    private final RoleService roleService;
    private final ShoppingListItemService shoppingListItemService;
    private final ShoppingListService shoppingListService;
    private final UserService userService;

    @GetMapping
    public List<EventDto> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "15") int size

    ) {
        if (page <= 0) {
            page = 1;
        }
        return eventService.findAll(page, size);
    }


    // Поиск событий по менеджеру события, id
    @GetMapping("/byManagerId/{id}")
    public List<EventDto> findEventsByManagerId(
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


    @PutMapping
    public ResponseEntity<?> updateEvent(@RequestBody Event event){
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/event/{id}")
    public void deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
    }

    @DeleteMapping("/expense/{id}")
    public void deleteExpense(@PathVariable Long id){
        expenseService.deleteExpense(id);
    }

    @DeleteMapping("/user/{id}")
    public void deleteEventByPrincipal(@PathVariable Long id){
        userService.deleteUser(id);
    }

}
