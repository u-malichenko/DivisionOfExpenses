package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.services.ExpenseService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/{id}")
    public Optional<Expense> findById(@PathVariable Long id){
        return expenseService.findById(id);
    }

    @GetMapping
    public Page<Expense> findAll(@RequestParam(required = false, defaultValue = "0") int page ,
                                 @RequestParam(required = false, defaultValue = "10") int size){
        return expenseService.findAll(page, size);
    }
}
