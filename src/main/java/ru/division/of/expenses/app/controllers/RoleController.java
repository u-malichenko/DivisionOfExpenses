package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.division.of.expenses.app.services.RoleService;

@Controller
@RequiredArgsConstructor
public class RoleController {

    private RoleService roleService;

}
