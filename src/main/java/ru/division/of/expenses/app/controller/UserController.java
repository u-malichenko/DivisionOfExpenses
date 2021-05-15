package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.division.of.expenses.app.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

}
