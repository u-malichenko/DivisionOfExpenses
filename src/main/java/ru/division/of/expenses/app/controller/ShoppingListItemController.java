package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.division.of.expenses.app.service.ShoppingListItemService;

@Controller
@RequiredArgsConstructor
public class ShoppingListItemController {

    private ShoppingListItemService shoppingListItemService;

}
