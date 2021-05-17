package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.division.of.expenses.app.service.ExpenseService;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private ExpenseService expenseService;

}
