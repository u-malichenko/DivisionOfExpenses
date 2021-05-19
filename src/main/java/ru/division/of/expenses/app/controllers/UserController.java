package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.division.of.expenses.app.services.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

}
