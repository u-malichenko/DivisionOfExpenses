package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.division.of.expenses.app.dto.MenuElementEvent;
import ru.division.of.expenses.app.models.User;
import ru.division.of.expenses.app.services.MenuElementEventService;
import ru.division.of.expenses.app.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menuElementEvent")
public class MenuElementEventController {
    private final MenuElementEventService menuElementEventService;
    private final UserService userService;

    @GetMapping
    public List<MenuElementEvent> findAllMenuElementEvents(
            Principal principal,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        String userName = principal.getName();
        Optional<User> user = userService.findUserByUsername(userName);
        Long userId = user.get().getId();
        return menuElementEventService.findAllMenuElementEvents(userId, page, size);
    }
}
