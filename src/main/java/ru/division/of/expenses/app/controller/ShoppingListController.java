package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.division.of.expenses.app.service.ShoppingListService;

@Controller
@RequiredArgsConstructor
public class ShoppingListController {

    private ShoppingListService shoppingListService;

}
