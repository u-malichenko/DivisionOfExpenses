package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.division.of.expenses.app.services.ShoppingListService;

@Controller
@RequiredArgsConstructor
public class ShoppingListController {

    private ShoppingListService shoppingListService;

}
