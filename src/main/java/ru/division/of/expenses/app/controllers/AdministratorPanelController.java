package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.services.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdministratorPanelController {

    private DirectPayersService directPayersService;
    private EventService eventService;
    private ExpenseService expenseService;
    private PartitialPayersService partitialPayersService;
    private RoleService roleService;
    private ShoppingListItemService shoppingListItemService;
    private ShoppingListService shoppingListService;
    private UserService userService;

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
