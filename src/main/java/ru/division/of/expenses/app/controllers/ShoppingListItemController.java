package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.division.of.expenses.app.services.ShoppingListItemService;

@Controller
@RequiredArgsConstructor
public class ShoppingListItemController {

    private ShoppingListItemService shoppingListItemService;

}
