package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.division.of.expenses.app.services.*;

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

}