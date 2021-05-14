package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.division.of.expenses.app.service.RoleService;

@Controller
@RequiredArgsConstructor
public class RoleController {

    private RoleService roleService;

}
