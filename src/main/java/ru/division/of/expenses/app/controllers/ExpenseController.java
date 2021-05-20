package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.division.of.expenses.app.services.ExpenseService;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private ExpenseService expenseService;

}
