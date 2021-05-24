package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.ExpenseDto;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.services.EventService;
import ru.division.of.expenses.app.services.ExpenseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return expenseService.findById(id);
    }

    @GetMapping
    public Page<Expense> findAll(@RequestParam(required = false, defaultValue = "1") int page ,
                                 @RequestParam(required = false, defaultValue = "5") int size){
        return expenseService.findAll(page, size);
    }

    @GetMapping("/ByEventId/{id}")
    public List<ExpenseDto> findExpenseByEventId(@PathVariable Long id,
                                            @RequestParam(required = false, defaultValue = "1") int page ,
                                            @RequestParam(required = false, defaultValue = "5") int size){
        return eventService.findExpenseById(id, page, size);
    }

    @PostMapping
    public Expense saveExpense(@RequestBody Expense expense) {
        return expenseService.saveExpense(expense);
    }

    @PutMapping
    public ResponseEntity<?> updateExpense(@RequestBody Expense expense){
        return expenseService.updateExpense(expense);
    }
}
